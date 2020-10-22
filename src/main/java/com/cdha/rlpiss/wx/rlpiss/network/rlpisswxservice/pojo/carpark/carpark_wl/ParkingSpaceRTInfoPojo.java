package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.carpark_wl;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestEnterOrOutDoorInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestNowInfo;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import lombok.Data;

/**
 * @author xgh
 * @version 1.0
 * @name ParkingSpaceRTInfoPojo.java
 * @brief 简要说明
 * @des 详细说明
 * @date 2020-10-19 14:03
 */

@Data
public class ParkingSpaceRTInfoPojo {

    private String appKey; //车场标识

    private String timeStamp; //时间戳

    private String sign; //签名

    private Integer place; //总车位数

    private Integer surplus; //剩余车位数

    private Integer fixed; //固定车数量

    private Integer interim; //临时车数量


    public static ParkingSpaceRTInfoPojo toWlData(CarParkRequestNowInfo info) {
        ParkingSpaceRTInfoPojo pojo = new ParkingSpaceRTInfoPojo();

        pojo.setAppKey(StringUtils.changNull(info.getAppkey()));//
        pojo.setTimeStamp(StringUtils.changNull(info.getTimestamp()));//
        pojo.setSign(StringUtils.changNull(info.getSign()));//
        pojo.setPlace(info.getPlace());
        pojo.setSurplus(info.getSurplus());
        pojo.setFixed(info.getFixed());
        pojo.setInterim(info.getInterim());


        return pojo;
    }


}
