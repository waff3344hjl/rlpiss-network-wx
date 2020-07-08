package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.wxController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.wechatsub.wxtools.common.WxConfig;
import com.cdha.wechatsub.wxtools.common.WxConsts;
import com.cdha.wechatsub.wxtools.modle.UserMSG;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonParser;

import java.io.IOException;


/**
 * 公众号网页授权openid 获取
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class WxOpenidController {


    @Autowired
    private WxConfig wxConfig;





    /**
     * 获取openid
     *
     * @param codeInfo 授权code
     * @return cod
     * @throws IOException 2
     */
    @CrossOrigin
    @RequestMapping(path = "/getOpenidByCode")
    public BaseResponseData<UserMSG> getopenid(@RequestBody BaseRequestData<String> codeInfo) throws IOException {
        log.info("===  微信授权模块  ==="+codeInfo.getRqData());
        BaseResponseData<UserMSG> result = new BaseResponseData<>();
        UserMSG msg = getUserinfo1(codeInfo.getRqData());
        if (msg != null) {
            result.setCode("0");
            result.setMsg("");
            result.setRsData(msg);
            return result;
        } else {
            result.setCode("-1");
            result.setMsg("微信授权失败");
            return result;
        }

    }


    /**
     * 根据code获取用户信息，包括openid等
     *
     * @param code 1
     * @return 1
     * @throws IOException 1
     */
    private UserMSG getUserinfo1(String code) throws IOException {
        UserMSG msg = new UserMSG();

        System.out.println(code);
        if (code != null) {
            //获取返回的code
            String requestUrl = WxConsts.URL_OAUTH2_GET_ACCESSTOKEN.replace("CODE", code)
                    .replace("APPID", wxConfig.getAppId())
                    .replace("SECRET", wxConfig.getAppSecret());
            log.info("=========================获取token==================="+requestUrl);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //向微信发送请求并获取response
            String response = httpClient.execute(httpGet, responseHandler);
            log.info("=========================获取token===================");
            log.info(response);
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(response);
            String access_token = jsonObject.get("access_token").getAsString();
            String openId = jsonObject.get("openid").getAsString();
            log.info("=======================用户access_token==============");
            log.info(access_token);
            log.info(openId);
            //获取用户基本信息的连接
            String getUserInfo = WxConsts.URL_OAUTH2_GET_USER_INFO;
            String userInfoUrl = getUserInfo.replace("ACCESS_TOKEN", access_token).replace("OPENID", openId);
            HttpGet httpGetUserInfo = new HttpGet(userInfoUrl);
            String userInfo = httpClient.execute(httpGetUserInfo, responseHandler);
            //微信那边采用的编码方式为ISO8859-1所以需要转化
            String json = new String(userInfo.getBytes("ISO-8859-1"), "UTF-8");
            log.info("====================userInfo==============================");
            JsonObject jsonObject1 = (JsonObject) parser.parse(json);
            String nickname = jsonObject1.get("nickname").getAsString();
            String city = jsonObject1.get("city").getAsString();
            String province = jsonObject1.get("province").getAsString();
            String country = jsonObject1.get("country").getAsString();
            String headimgurl = jsonObject1.get("headimgurl").getAsString();
            String id = jsonObject1.get("openid").getAsString();
            //性别  1 男  2 女  0 未知
            Integer sex = jsonObject1.get("sex").getAsInt();
            log.info("openid" + id);
            log.info("昵称" + nickname);
            log.info("城市" + city);
            log.info("省" + province);
            log.info("国家" + country);
            log.info("头像" + headimgurl);
            log.info("性别" + sex);
            log.info(userInfo);

            msg.setOpenid(id);
            msg.setUserNC(nickname);
            msg.setSex(sex + "");//性别  1 男  2 女  0 未知
            msg.setPath(headimgurl);
            msg.setCity(city);
            msg.setProvince(province);
            msg.setCountry(country);
//            return openId;
            return msg;
        }
        return null;
    }


}
