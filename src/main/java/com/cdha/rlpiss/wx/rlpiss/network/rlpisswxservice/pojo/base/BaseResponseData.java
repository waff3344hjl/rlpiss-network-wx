package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回基类
 * code：访问标识 0 =成功
 * msg：错误描述
 * data：返回实例
 * @param <T>
 */
@Data
public class BaseResponseData<T> implements Serializable {

    String code;
    String msg;
    T rsData;

    public BaseResponseData(String code, String msg, T rsData) {
        this.code = code;
        this.msg = msg;
        this.rsData = rsData;
    }

    public BaseResponseData() {
    }
}
