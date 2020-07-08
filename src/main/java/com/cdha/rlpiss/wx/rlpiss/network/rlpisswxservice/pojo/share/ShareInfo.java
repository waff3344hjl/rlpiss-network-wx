package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.share;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 分享
 */
@Data
public class ShareInfo implements Serializable {
    String xdq; //需求单信息
    String xqdLx;//需求单类型    整车到货 0，整车发货1，集装箱到货2，集装箱发货3.
    User sj;//司机ID or司机微信ID
    User hz;//货主id or 货主微信ID
}
