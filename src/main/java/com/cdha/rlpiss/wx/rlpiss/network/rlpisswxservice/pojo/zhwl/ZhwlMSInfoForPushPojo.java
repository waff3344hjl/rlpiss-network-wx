package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户关系（推送使用）
 *
 * @author xgh
 * @date 2020-07-20
 */

@Data
public class ZhwlMSInfoForPushPojo implements Serializable {
    //被添加人信息
    private String yhid;

    private String yhxm;

    private String sjh;

    private String wxopenid;

    //添加人的信息
    private String qqryhid;

    private String qqryhxm;

    private String qqrsjh;

    private String qqrwxopenid;

}
