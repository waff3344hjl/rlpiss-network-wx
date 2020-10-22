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
@EqualsAndHashCode(callSuper = true)
@Data
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

}
