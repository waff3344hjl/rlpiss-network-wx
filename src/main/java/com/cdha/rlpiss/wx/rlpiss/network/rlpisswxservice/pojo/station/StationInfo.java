package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 车站
 * @Author HJL
 * @Date Created in 2020-04-20
 */
@Data
public class StationInfo implements Serializable {

    private String stationId;
    private String stationName;
    private String stationTms;
    private String stationDbm;
    private String stationJdm;

}
