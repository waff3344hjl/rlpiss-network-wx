package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 发到货查询。。。查询需求单
 * @Author HJL
 * @Date Created in 2020-04-17
 */

@Data
public class QueryXQDInfo implements Serializable {

    private User user;
    private String type; //发、到类型
}
