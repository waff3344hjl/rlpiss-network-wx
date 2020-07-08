package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.order;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.YuYueAddData;
import lombok.Data;

import java.io.Serializable;

/**
 * 预约进门信息
 */
@Data
public class RlpissOrderInfo implements Serializable {
    private User user;
    private YuYueAddData data;
    private String delCph;//需要撤销的车牌号


}
