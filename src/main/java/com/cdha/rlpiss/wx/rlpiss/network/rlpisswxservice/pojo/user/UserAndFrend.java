package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 货主查询司机信息时候，请求体
 * @Author HJL
 * @Date Created in 2020-04-24
 */
@Data
public class UserAndFrend implements Serializable {
    User hz;
    User sj;
}
