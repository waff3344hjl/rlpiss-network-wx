package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.CyCzxxAddData;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-17
 */
@Data
public class StationRequest  implements Serializable {
    private StationInfo stationInfo;
    private User user;
    private CyCzxxAddData cyczData;
}
