package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 多式联运返回体基类
 * @Author HJL
 * @Date Created in 2020-05-14
 */
@Data
public class BaseDSLYReData<T> implements Serializable {
    private String msg;
    private String returnCode;
    private T data;

    //首次获取Token，及耍Token-返回体
    @Data
    public static class TokenGet{
        private String accessToken;
        private String refreshToken;
    }



}
