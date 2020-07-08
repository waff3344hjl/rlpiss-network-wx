package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.order;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Data
public class RlpissOrderJzxInfo implements Serializable {
    private User user;
    private StationInfo stationInfo;
}
