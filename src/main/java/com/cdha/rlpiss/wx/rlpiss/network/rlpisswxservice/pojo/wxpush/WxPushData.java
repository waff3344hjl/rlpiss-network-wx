package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.wxpush;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 微信推送模版
 * @Author HJL
 * @Date Created in 2020-04-30
 */
@Data
public class WxPushData implements Serializable {
    private String value;
    private String color;

    public WxPushData(String value, String color) {
        this.value = value;
        this.color = color;
    }
}
