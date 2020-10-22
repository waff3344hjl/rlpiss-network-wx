package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.carpark_wl;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestEnterOrOutDoorInfo;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import lombok.Data;

/**
 * @name ParkingEnterInfoPojo.java
 * @brief 车辆进场信息
 *
 * @des 停车场管理，车辆进场信息
 *
 * @author xgh
 * @date 2020-10-19 13:34
 * @version 1.0
 */

@Data
public class ParkingEnterInfoPojo {

    private String id; //进出记录ID

    private String appKey; //车场标识

    private String enterTimeStamp; //时间戳

    private String sign; //签名

    private String cardID; //卡号

    private String memberID; //会员ID

    private String memberType; //会员类型

    private String name; //会员姓名

    private String plateNumber; //车牌号

    private String plateColor; //车牌颜色

    private String enterChannel; //入场通道

    private String enterTime; //入场时间

    private Integer subPlace; //是否在库（0：否，1：是）

    private String image; //抓拍图片(base64)

    private String remark; //备注

    public static ParkingEnterInfoPojo toWlData(CarParkRequestEnterOrOutDoorInfo info) {
        ParkingEnterInfoPojo pojo = new ParkingEnterInfoPojo();
        pojo.setId(StringUtils.changNull(info.getId()));//
        pojo.setAppKey(StringUtils.changNull(info.getAppkey()));//
        pojo.setEnterTimeStamp(StringUtils.changNull(info.getTimestamp()));//
        pojo.setSign(StringUtils.changNull(info.getSign()));//
        pojo.setCardID(StringUtils.changNull(info.getCardid()));//
        pojo.setMemberID(StringUtils.changNull(info.getMemberid()));//
        pojo.setMemberType(StringUtils.changNull(info.getMembertype()));//
        pojo.setName(StringUtils.changNull(info.getName()));//
        pojo.setPlateNumber(StringUtils.changNull(info.getPlatenumber()));//
        pojo.setPlateColor(StringUtils.changNull(info.getPlatecolor()));//
        pojo.setEnterChannel(StringUtils.changNull(info.getEnterchannel()));
        pojo.setEnterTime(StringUtils.changNull(info.getEntertime()));
        pojo.setSubPlace(info.getSubplace());//
        pojo.setImage(StringUtils.changNull(info.getImage()));//
        pojo.setRemark(StringUtils.changNull(info.getRemark()));//




        return pojo;
    }
}
