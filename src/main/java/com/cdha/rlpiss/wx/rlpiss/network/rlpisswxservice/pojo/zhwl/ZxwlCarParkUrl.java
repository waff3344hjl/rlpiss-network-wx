package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl
 * @ClassName: ZxwlCarParkUrl
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 15:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 15:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class ZxwlCarParkUrl implements Serializable {
    private String carpark_id; //< 主键
    private String appkey;//< 停车系统标记
    private String secretkey;//< 停车系统标记2
    private String sa_id;//< 物流部署地址ID
}
