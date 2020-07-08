package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.wxpush;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description  微信推送模版
 * @Author HJL
 * @Date Created in 2020-04-30
 */
@Data
public class WxPushDataMsg  implements Serializable {
    private WxPushData first;//标题
    private WxPushData keyword1;//运单号
    private WxPushData keyword2;//车号
    private WxPushData keyword3;//发到站
    private WxPushData keyword4;//装车开始时间
    private WxPushData keyword5;//装车完成时间
    private WxPushData keyword6;//装车完成时间
    private WxPushData keyword7;//装车完成时间
    private WxPushData keyword8;//装车完成时间
    private WxPushData keyword9;//装车完成时间
    private WxPushData keyword10;//装车完成时间
    private WxPushData remark;//备注_货位名称

}
