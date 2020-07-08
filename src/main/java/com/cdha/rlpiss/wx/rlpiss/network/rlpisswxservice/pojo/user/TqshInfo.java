package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 提前上货--前台请求体
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@Data
public class TqshInfo  implements Serializable {

    private User user;
    private String tyrName;
}
