package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.dsly_service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IzhwlUrlDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlURL;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.UrlConfig;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.AesEncrypt;
import com.cdha.wechatsub.wxtools.common.DuanXinYZConfig;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Description 多式联运接口服务
 * @Author HJL
 * @Date Created in 2020-06-01
 */
@Slf4j
@Service
public class DSLYService {


    private final RestTemplateManager manager;
    private final UrlConfig urlConfig;
    private final IzhwlUrlDao dao;
    private final DuanXinYZConfig yzConfig;

    @Autowired
    public DSLYService(RestTemplateManager manager, UrlConfig urlConfig, IzhwlUrlDao dao, DuanXinYZConfig yzConfig) {
        this.manager = manager;
        this.urlConfig = urlConfig;
        this.dao = dao;
        this.yzConfig = yzConfig;
    }

    /**
     * 多式联运---------访问数据
     *
     * @param rqData 多式联运---------请求体
     * @return json对象
     */
    public JSONObject getDataByDsly(BaseDSLYRqData<BaseDSLYRqData.BusinessData> rqData) {
        checkBusinessData(rqData);
        try {
            log.info("====    测试    ====" + rqData.toString());
            log.info("====    测试    ====" + rqData.getData().getUrl());
            log.info("====    测试    ====" + rqData.getData().getLj());
            log.info("====    测试    =jiexi===" + AesEncrypt.desEncrypt(rqData.getData().getData()));
            String rqString = manager.getRlpissDataString(urlConfig.getDslyUrl(), JSON.toJSONString(rqData));
            log.info("====    测试   jg ====" + rqString);
            return JSONObject.parseObject(rqString.trim());
        } catch (Exception e) {
            log.error("=======  获取数据异常  ====== ：" + e.getMessage());
            throw new GmManagerException("=======  获取数据异常  ====== ：" + e.getMessage(), "dsly");
        }

    }

    private void checkBusinessData(BaseDSLYRqData<BaseDSLYRqData.BusinessData> rqData) {
        if (StringUtils.isEmpty(rqData.getGrant_Type())) {
            log.error("=======  获取数据参数异常  ====== ：" + rqData.toString());
            log.error("=======  获取数据参数异常  ====== ：" + "Grant_Type is null");
            throw new GmManagerException("=======  获取数据参数异常  ====== ：" + "Grant_Type is null", "dsly");
        }
        if (StringUtils.isEmpty(rqData.getAuthorization())) {
            log.error("=======  获取数据参数异常  ====== ：" + "Authorization is null");
            throw new GmManagerException("=======  获取数据参数异常  ====== ：" + "Authorization is null", "dsly");
        }

        if (rqData.getData() == null) {
            log.error("=======  获取数据参数异常  ====== ：" + "Data is null");
            throw new GmManagerException("=======  获取数据参数异常  ====== ：" + "Data is null", "dsly");
        }
    }

    public List<ZxwlURL> getAllUrls() {
        List<ZxwlURL> urlInfo = dao.getAllUrls();
        if (urlInfo.size() > 0) {
            return urlInfo;
        } else {
            throw new GmManagerException("urlInfo is null", "dsly");
        }
    }

    public BaseDSLYReData<User> getYzm(BaseDSLYRqData<String> data) {
        log.info(data.toString());
        //生成6位验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //构造client
        ZhenziSmsClient client = new ZhenziSmsClient(yzConfig.getApiurl(), yzConfig.getAppID(), yzConfig.getAppSecret());

        try {
            //构造短信参数

            //发送client
            String result = client.send(getParams(data.getData(), verifyCode));
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(result);
            System.out.println("jsonObject_" + jsonObject);
            if (jsonObject.get("code").getAsInt() != 0) {
                //短信平台返回：发送失败
                BaseDSLYReData<User> reData = new BaseDSLYReData<User>();
                reData.setReturnCode("-99");
                reData.setMsg("msg send fail");
                return reData;
            } else {
                BaseDSLYReData<User> responseData = new BaseDSLYReData<User>();
                User u = new User();
                u.setSjh(data.getData());
                u.setDxyzm(verifyCode);
                responseData.setReturnCode("0");
                responseData.setData(u);
                log.info("==== yzm ===  [ phone =" + data.getData() + "]  ;  [yzm =" + verifyCode + "]");
                return responseData;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new GmManagerException(e.getMessage());
        }
    }


    /**
     * 构造短信验证码访问参数
     *
     * @param phone      电话
     * @param verifyCode 验证码
     * @return 1
     */
    private Map<String, Object> getParams(String phone, String verifyCode) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("number", phone);
        params.put("templateId", "40");
        String[] templateParams = new String[2];
        templateParams[0] = verifyCode;
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);

        return params;
    }
}
