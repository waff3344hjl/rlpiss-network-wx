package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark
 * @ClassName: CarParkRequestData
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 10:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 10:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class CarParkRequestData implements Serializable {

    private String appkey;//<	车场标识
    private String timestamp;//<	10位时间戳
    private String sign;//<	签名
}
