package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config;


import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
@Configuration
@ConfigurationProperties
public class JiGuangConfig {
    // 极光官网-个人管理中心-appkey
    @Value("${push.appkey}")
    private String appkey;
    // 极光官网-个人管理中心-点击查看-secret
    @Value("${push.secret}")
    private String secret;
    private JPushClient jPushClient;
    // 推送客户端
    @PostConstruct
    public void initJPushClient() {
        jPushClient = new JPushClient(secret, appkey);
    }
    // 获取推送客户端
    public JPushClient getJPushClient() {
        return jPushClient;
    }

    public void setjPushClient(JPushClient jPushClient) {
        this.jPushClient = jPushClient;
    }
}


