package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.carpark_service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.UrlConfig;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IzhwlCarParkDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.carpark_wl.ParkingEnterInfoPojo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.carpark_wl.ParkingLeaveInfoPojo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.carpark_wl.ParkingSpaceRTInfoPojo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlURL;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.AesEncrypt;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.carpark_service
 * @ClassName: CarParkService
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 10:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 10:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Slf4j
@Service
public class CarParkService {

    private final UrlConfig urlConfig;
    private final RestTemplateManager manager;
    private final IzhwlCarParkDao dao;

    @Autowired
    public CarParkService(UrlConfig urlConfig, RestTemplateManager manager, IzhwlCarParkDao dao) {
        this.urlConfig = urlConfig;
        this.manager = manager;
        this.dao = dao;
    }

    /**
     * 1、车辆入场 数据上传
     *
     * @param data 上传的数据
     * @return 是否处理成功
     */
    public CarParkResponseData pushEnterDoorTask(CarParkRequestEnterOrOutDoorInfo data) {
//        CarParkRequestEnterOrOutDoorInfo data = (CarParkRequestEnterOrOutDoorInfo) data1;
        log.warn("Hujl--jm===========>" + data.toString());
        checkData(data);//<检查参数---进门
        try {

//            CarParkUrl carUrl = dao.getIpByAppkey(data.getAppkey());//根据APPkey查询数据库中对应配置的Appkey  and secretkey
            CarParkUrl carUrl = dao.getIpByAppkey("123456");//测试---appkey写死
            if (carUrl == null) {
                throw new RuntimeException("停车场系统对应货场服务IP未设置");
            }
            CarParkResponseData result = new CarParkResponseData();
            String sign = data.getSign();
            StringBuffer mSignBf = new StringBuffer()
                    .append(carUrl.getAppKey())
                    .append(data.getTimestamp())
                    .append(carUrl.getSecretkey());
            String mSign = DigestUtils.md5DigestAsHex(new String(mSignBf).getBytes());
            log.info("我的签名==========>" + mSign);
            //判断签名是否正确
//            if (sign.equals(mSign)) {
            if (true) { //测试不验证签名
                String rqSting = getWlRqStingForJm(data);//请求体转化为 多式联运 请求体=-----进门
                BaseDSLYRqData<BaseDSLYRqData.BusinessData> dslyRqData = getDslyRqData(carUrl, rqSting, data.getAppkey(), urlConfig.getPushJsqUrl(), urlConfig.getPushTccJm());////构造成多式联运的请求结构
                String rqString = manager.getRlpissDataString(urlConfig.getDslyUrl(), JSON.toJSONString(dslyRqData));//发起请求

                JSONObject object = JSONObject.parseObject(rqString.trim());//请求结果转化为object
                String returnCode = object.getString("returnCode");//取值 returnCode
                if ("0".equals(returnCode)) {
                    //判断访问多式联运是否成功
                    String dslyData = object.getString("data");
                    String aesDslyData = AesEncrypt.desEncrypt(dslyData.trim());//解密
                    JSONObject objectWl = JSONObject.parseObject(aesDslyData.trim());//字符串转object
                    log.info("Hujl===========>jc -->" + objectWl.toJSONString());
                    String wlCode = objectWl.getString("code");
                    if ("0".equals(wlCode)) {
                        //判断物流服务是否访问成功
                        result.setCode(0);
                    } else {
                        result.setCode(-4);
                        result.setMsg("异常：" + objectWl.getString("msg"));
                    }

                } else {
                    result.setCode(-3);
                    result.setMsg("网络错误，上传失败");
                }
                return result;
            } else {
                result.setCode(-2);
                result.setMsg("签名不合法");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 1、车辆出门 数据上传
     *
     * @param data 上传的数据
     * @return 是否处理成功
     */
    public CarParkResponseData outDoorTask(CarParkRequestEnterOrOutDoorInfo data) {
        log.warn("Hujl--cm===========>" + data.toString());
        checkData(data);//<检查参数---出门
        try {

//            CarParkUrl carUrl = dao.getIpByAppkey(data.getAppkey());//根据APPkey查询数据库中对应配置的Appkey  and secretkey
            CarParkUrl carUrl = dao.getIpByAppkey("123456");//测试---appkey写死
            if (carUrl == null) {
                throw new RuntimeException("停车场系统对应货场服务IP未设置");
            }
            CarParkResponseData result = new CarParkResponseData();
            String sign = data.getSign();
            StringBuffer mSignBf = new StringBuffer()
                    .append(carUrl.getAppKey())
                    .append(data.getTimestamp())
                    .append(carUrl.getSecretkey());
            String mSign = DigestUtils.md5DigestAsHex(new String(mSignBf).getBytes());
            log.info("我的签名==========>" + mSign);
            //判断签名是否正确
//            if (sign.equals(mSign)) {
            if (true) { //测试不验证签名
                String rqSting = getWlRqStingForCm(data);//请求体转化为 多式联运 请求体=-----出门
                BaseDSLYRqData<BaseDSLYRqData.BusinessData> dslyRqData = getDslyRqData(carUrl, rqSting, data.getAppkey(), urlConfig.getPushJsqUrl(), urlConfig.getPushTccCm());////构造成多式联运的请求结构
                String rqString = manager.getRlpissDataString(urlConfig.getDslyUrl(), JSON.toJSONString(dslyRqData));//发起请求
                JSONObject object = JSONObject.parseObject(rqString.trim());//请求结果转化为object
                String returnCode = object.getString("returnCode");//取值 returnCode
                if ("0".equals(returnCode)) {
                    //判断访问多式联运是否成功
                    String dslyData = object.getString("data");
                    String aesDslyData = AesEncrypt.desEncrypt(dslyData.trim());//解密
                    JSONObject objectWl = JSONObject.parseObject(aesDslyData.trim());
                    log.info("Hujl===========>c m -->" + objectWl.toJSONString());
                    String wlCode = objectWl.getString("code");
                    if ("0".equals(wlCode)) {
                        //判断物流服务是否访问成功
                        result.setCode(0);
                    } else {
                        result.setCode(-4);
                        result.setMsg("异常：" + objectWl.getString("msg"));
                    }
                } else {
                    result.setCode(-3);
                    result.setMsg("网络错误，上传失败");
                }
                return result;
            } else {
                result.setCode(-2);
                result.setMsg("签名不合法");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getWlRqStingForJm(CarParkRequestEnterOrOutDoorInfo data) {
        BaseRequestData<ParkingEnterInfoPojo> baseRequestData = new BaseRequestData<>();
        ParkingEnterInfoPojo wlData = ParkingEnterInfoPojo.toWlData(data);
        baseRequestData.setMethod("tcc");
        baseRequestData.setRqData(wlData);
        String jsonString = getJsonString(baseRequestData);
        log.info("jm===========>" + baseRequestData.toString());
        String aesString = getString(new StringBuffer(), jsonString);
        return aesString;
    }

    private String getWlRqStingForCm(CarParkRequestEnterOrOutDoorInfo data) {

        BaseRequestData<ParkingLeaveInfoPojo> baseRequestData = new BaseRequestData<>();
        ParkingLeaveInfoPojo wlData = ParkingLeaveInfoPojo.toWlData(data);

        baseRequestData.setMethod("tcc");
        baseRequestData.setRqData(wlData);
        String jsonString = getJsonString(baseRequestData);
        log.info("cm===========>" + baseRequestData.toString());
        String aesString = getString(new StringBuffer(), jsonString);
        return aesString;
    }

    /**
     * 车位实时信息上传
     *
     * @param data 上传的数据
     * @return 是否处理成功
     */
    public CarParkResponseData pushCarparkInfo(CarParkRequestNowInfo data) {
        log.warn("Hujl--nowinfo===========>" + data.toString());
        checkData(data);//<检查参数
        try {

//            CarParkUrl carUrl = dao.getIpByAppkey(data.getAppkey());//根据APPkey查询数据库中对应配置的Appkey  and secretkey
            CarParkUrl carUrl = dao.getIpByAppkey("123456");//测试---appkey写死
            if (carUrl == null) {
                throw new RuntimeException("停车场系统对应货场服务IP未设置");
            }
            CarParkResponseData result = new CarParkResponseData();
            String sign = data.getSign();
            StringBuffer mSignBf = new StringBuffer()
                    .append(carUrl.getAppKey())
                    .append(data.getTimestamp())
                    .append(carUrl.getSecretkey());
            String mSign = DigestUtils.md5DigestAsHex(new String(mSignBf).getBytes());
            log.info("我的签名==========>" + mSign);
            //判断签名是否正确
//            if (sign.equals(mSign)) {
            if (true) { //测试不验证签名
                String rqSting = getWlRqStingForTccInfo(data);//请求体转化为 多式联运 请求体
                BaseDSLYRqData<BaseDSLYRqData.BusinessData> dslyRqData = getDslyRqData(carUrl, rqSting, data.getAppkey(), urlConfig.getPushJsqUrl(), urlConfig.getPushTccInfo());////构造成多式联运的请求结构
                String rqString = manager.getRlpissDataString(urlConfig.getDslyUrl(), JSON.toJSONString(dslyRqData));//发起请求
                JSONObject object = JSONObject.parseObject(rqString.trim());//请求结果转化为object
                String returnCode = object.getString("returnCode");//取值 returnCode
                if ("0".equals(returnCode)) {
                    //判断访问多式联运是否成功
                    String dslyData = object.getString("data");
                    String aesDslyData = AesEncrypt.desEncrypt(dslyData.trim());//解密
                    JSONObject objectWl = JSONObject.parseObject(aesDslyData.trim());
                    log.info("Hujl===========>now info -->" + objectWl.toJSONString());
                    String wlCode = objectWl.getString("code");
                    if ("0".equals(wlCode)) {
                        //判断物流服务是否访问成功
                        result.setCode(0);
                    } else {
                        result.setCode(-4);
                        result.setMsg("异常：" + objectWl.getString("msg"));
                    }
                } else {
                    result.setCode(-3);
                    result.setMsg("网络错误，上传失败");
                }
                return result;
            } else {
                result.setCode(-2);
                result.setMsg("签名不合法");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    private BaseDSLYRqData<BaseDSLYRqData.BusinessData> getDslyRqData(CarParkUrl carUrl, String aesString, String appkey, String jsqPath, String wlPath) {
        //构造成多式联运的请求结构
        BaseDSLYRqData<BaseDSLYRqData.BusinessData> dslyRqData = newInstanceDslyModel(
                carUrl.getHyzUrl(),
                carUrl.getJsqUrl() + jsqPath,
                carUrl.getWlUrl() + wlPath,
                aesString,
                "",
                ""
        );
        return dslyRqData;

    }

    private String getWlRqStingForTccInfo(CarParkRequestNowInfo data) {

        BaseRequestData<ParkingSpaceRTInfoPojo> baseRequestData = new BaseRequestData<>();
        ParkingSpaceRTInfoPojo wlPojo = ParkingSpaceRTInfoPojo.toWlData(data);
        baseRequestData.setMethod("tcc");
        baseRequestData.setRqData(wlPojo);
        String jsonString = getJsonString(baseRequestData);
        String aesString = getString(new StringBuffer(), jsonString);
        return aesString;
    }

    private void checkData(CarParkRequestData data) {
        if (StringUtils.isBlank(data.getAppkey()) || StringUtils.isBlank(data.getTimestamp()) || StringUtils.isBlank(data.getSign())) {
            throw new RuntimeException("参数异常");
        }

    }


    /**
     * 构造多式联运请求体
     *
     * @param hyzUrl     货运站转发器URL
     * @param jsqUrl     物流接收器URL
     * @param meUrl      物流真实服务RUL
     * @param dataString 多式联运JSON串
     * @param aToken     多式联运Token
     * @param type       多式联运TYPE
     * @return D
     */
    private BaseDSLYRqData<BaseDSLYRqData.BusinessData> newInstanceDslyModel(String hyzUrl, String jsqUrl, String meUrl, String dataString, String aToken, String type) {
        StringBuffer bf = new StringBuffer();
        bf.append(jsqUrl).append("#").append(meUrl);
        BaseDSLYRqData<BaseDSLYRqData.BusinessData> dataBaseDSLYRqData = new BaseDSLYRqData<>();
        BaseDSLYRqData.BusinessData businessData = new BaseDSLYRqData.BusinessData();
        businessData.setData(dataString);
        businessData.setLj(new String(bf));
        businessData.setUrl(hyzUrl);

        dataBaseDSLYRqData.setAuthorization(aToken);
        dataBaseDSLYRqData.setGrant_Type(type);
        dataBaseDSLYRqData.setData(businessData);
        return dataBaseDSLYRqData;
    }

    //对象转json串
    private String getJsonString(Object wlrq) {
        return JSON.toJSONString(wlrq);
    }

    //json串进行AES加密
    private String getString(StringBuffer jsonBf, String jsonString) {
        try {
            jsonBf.append(AesEncrypt.encrypt(jsonString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(jsonBf);
    }
}