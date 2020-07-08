package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 多少联运 请求基类
 * @Author HJL
 * @Date Created in 2020-05-14
 */
@Data
public class BaseDSLYRqData<T> implements Serializable {
    private String grant_Type;
    private String authorization;

    private T data;


    //刷新token
    @Data
    public static class RfToken {
        private String refreshToken;
    }

    //首次获取token
    @Data
    public static class AppId {
        private String appid;
        private String appsecret;
    }

    //业务请求参数
    @Data
    public static class BusinessData {
        private String data;
        private String lj;
        private String url;
    }


}


