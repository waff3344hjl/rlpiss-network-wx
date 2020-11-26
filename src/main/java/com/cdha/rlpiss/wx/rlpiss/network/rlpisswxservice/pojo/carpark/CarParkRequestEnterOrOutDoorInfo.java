package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark
 * @ClassName: CarParkRequestEnterOrOutDoorInfo
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 11:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 11:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class CarParkRequestEnterOrOutDoorInfo extends CarParkRequestData implements Serializable {
    private String id;//<	进出记录ID
    private String cardid;//<	卡号
    private String memberid;//<	会员ID
    private String membertype;//<	会员类型
    private String name;//<	会员姓名
    private String platenumber;//<	车牌号
    private String platecolor;//<	车牌颜色
    private String enterchannel;//<	入场通道
    private String entertime;//<	入场时间
    private String money;//<	停车费用
    private String leavechannel;//<	出场通道
    private String leavetime;//<	出场时间
    private int subplace;//<	是否库中库0、不是库中库，1、是库中库
    private String image;//<	抓拍图片(base64)
    private String remark;//<	备注
    private String Long; //< 	停车时长（分钟）

    @Override
    public String toString() {
        return "CarParkRequestEnterOrOutDoorInfo{" +
                "id='" + id + '\'' +
                ", cardid='" + cardid + '\'' +
                ", memberid='" + memberid + '\'' +
                ", membertype='" + membertype + '\'' +
                ", name='" + name + '\'' +
                ", platenumber='" + platenumber + '\'' +
                ", platecolor='" + platecolor + '\'' +
                ", enterchannel='" + enterchannel + '\'' +
                ", entertime='" + entertime + '\'' +
                ", money='" + money + '\'' +
                ", leavechannel='" + leavechannel + '\'' +
                ", leavetime='" + leavetime + '\'' +
                ", subplace=" + subplace +
                ", remark='" + remark + '\'' +
                ", Long='" + Long + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public String getPlatecolor() {
        return platecolor;
    }

    public void setPlatecolor(String platecolor) {
        this.platecolor = platecolor;
    }

    public String getEnterchannel() {
        return enterchannel;
    }

    public void setEnterchannel(String enterchannel) {
        this.enterchannel = enterchannel;
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLeavechannel() {
        return leavechannel;
    }

    public void setLeavechannel(String leavechannel) {
        this.leavechannel = leavechannel;
    }

    public String getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    public int getSubplace() {
        return subplace;
    }

    public void setSubplace(int subplace) {
        this.subplace = subplace;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }
}
