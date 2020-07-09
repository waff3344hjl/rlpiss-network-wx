package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages={"com.cdha.wechatsub.wxtools","com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice"})
@MapperScan("com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao")
@EnableCaching //启用springboot 自带缓存SimpleCacheConfiguration
public class WxServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxServiceApplication.class, args);
    }

}
