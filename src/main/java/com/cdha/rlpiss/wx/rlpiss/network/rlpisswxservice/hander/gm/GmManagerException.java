package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm;

import lombok.Data;

import javax.naming.ldap.PagedResultsControl;

/**
 * 用户模块异常类
 */
@Data
public class GmManagerException extends RuntimeException {
    private String msg;
    private String code;

    public GmManagerException(String msg,String code){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public GmManagerException(String msg){
        super(msg);
        this.msg = msg;
    }


//    public GmManagerException(String message) {
//        super(message);
//    }
}
