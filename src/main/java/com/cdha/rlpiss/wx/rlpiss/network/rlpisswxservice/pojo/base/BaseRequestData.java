package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 参数基类
 * method ：方法名
 * rqData：参数实例
 * @param <T>
 */
@Data
public class BaseRequestData<T> implements Serializable {
    String method;
    T rqData;


    public BaseRequestData(String method, T rqData) {
        this.method = method;
        this.rqData = rqData;
    }

    public BaseRequestData(String method) {
        this.method = method;
    }



    public BaseRequestData(T rqData) {
        this.rqData = rqData;
    }
    public BaseRequestData() {

    }
}
