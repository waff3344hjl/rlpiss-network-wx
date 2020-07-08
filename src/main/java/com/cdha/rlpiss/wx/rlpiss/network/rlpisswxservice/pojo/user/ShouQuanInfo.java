package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 用户给好友授权---请求体
 * @Author HJL
 * @Date Created in 2020-05-21
 */
@Data
public class ShouQuanInfo implements Serializable {
    private User user;
    private String frendPhone;
}
