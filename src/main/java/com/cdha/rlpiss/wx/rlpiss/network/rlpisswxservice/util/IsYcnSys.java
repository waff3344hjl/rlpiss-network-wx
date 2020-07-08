package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util;

/**
 * @Description 判断是否为银川南的注册用户
 * @Author HJL
 * @Date Created in 2020-04-24
 */
public class IsYcnSys {
    public static boolean isYcnUser(String fwq){
        return "银川南".equals(fwq);
    }
}
