package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-30
 */
@Component
@ConfigurationProperties(prefix = "baseurl.configs")
@Getter
@Setter
public class UrlConfig {

    private String mUrl;

    private String dslyUrl;

    private String pushDhUrl;
    private String pushFhUrl;
    private String pushYswcUrl;
    private String pushJsqUrl;
    private String pushGetPushInfo;
    private String pushNewTastUrl;
}
