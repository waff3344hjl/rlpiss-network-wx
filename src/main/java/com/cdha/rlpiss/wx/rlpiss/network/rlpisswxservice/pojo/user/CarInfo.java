package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 功能描述：常用车辆维护
 * @Description 常用车辆
 * @Author HJL
 * @Date Created in 2020-04-16
 */
@Data
public class CarInfo implements Serializable {

    private String clxxid;//id

    private String cph;//车牌号


    private String cllx;//车辆类型


    private String zz;//载重


    private String wlgs;//物流公司


    private String sjh;//手机号


    private String sjxm;//司机姓名


    private String sjsjh;//司机手机号
}
