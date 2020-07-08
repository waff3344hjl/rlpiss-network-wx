package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-17
 */
@Data
public class CarRequest implements Serializable {
    private CarInfo carInfo;
    private User user;
}
