package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.carpark_wl;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestEnterOrOutDoorInfo;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import lombok.Data;

/**
 * @author xgh
 * @version 1.0
 * @name ParkingLeaveInfoPojo.java
 * @brief 车辆出场信息
 * @des 停车场管理，车辆出场信息
 * @date 2020-10-19 13:54
 */

@Data
public class ParkingLeaveInfoPojo {

    private String id; //进出记录ID

    private String appKey; //车场标识

    private String leaveTimeStamp; //时间戳

    private String sign; //签名

    private String cardID; //卡号

    private String memberID; //会员ID

    private String memberType; //会员类型

    private String name; //会员姓名

    private String plateNumber; //车牌号

    private String plateColor; //车牌颜色

    private String longTime; //停车时长（分钟）

    private String money; //停车费用

    private String leaveChannel; //出场通道

    private String leaveTime; //出场时间

    private Integer subPlace; //是否在库（0：否，1：是）

    private String image; //抓拍图片(base64)

    private String remark; //备注


    public static ParkingLeaveInfoPojo toWlData(CarParkRequestEnterOrOutDoorInfo info) {
        ParkingLeaveInfoPojo pojo = new ParkingLeaveInfoPojo();
        pojo.setId(StringUtils.changNull(info.getId()));//
        pojo.setAppKey(StringUtils.changNull(info.getAppkey()));//
        pojo.setLeaveTime(StringUtils.changNull(info.getTimestamp()));//
        pojo.setSign(StringUtils.changNull(info.getSign()));//
        pojo.setCardID(StringUtils.changNull(info.getCardid()));//
        pojo.setMemberID(StringUtils.changNull(info.getMemberid()));//
        pojo.setMemberType(StringUtils.changNull(info.getMembertype()));//
        pojo.setName(StringUtils.changNull(info.getName()));//
        pojo.setPlateNumber(StringUtils.changNull(info.getPlatenumber()));//
        pojo.setPlateColor(StringUtils.changNull(info.getPlatecolor()));//
        pojo.setLongTime(StringUtils.changNull(info.getLong()));//
        pojo.setMoney(StringUtils.changNull(info.getMoney()));//
        pojo.setLeaveChannel(StringUtils.changNull(info.getLeavechannel()));//
        pojo.setLeaveTime(StringUtils.changNull(info.getLeavetime()));//
        pojo.setSubPlace(info.getSubplace());//
        pojo.setImage(StringUtils.changNull(info.getImage()));//
        pojo.setRemark(StringUtils.changNull(info.getRemark()));//




        return pojo;
    }
}
