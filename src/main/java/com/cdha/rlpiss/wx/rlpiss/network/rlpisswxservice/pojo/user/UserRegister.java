package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.SFHRName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-20
 */
@Data
public class UserRegister extends User implements Serializable {
    private StationInfo stationInfo;

    private CarInfo carInfo;

    private SFHRName sfhrName;
}
