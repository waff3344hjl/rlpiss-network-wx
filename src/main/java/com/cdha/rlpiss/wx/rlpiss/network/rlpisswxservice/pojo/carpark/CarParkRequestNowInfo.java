package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark
 * @ClassName: CarParkRequestNowInfo
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 11:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 11:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CarParkRequestNowInfo extends CarParkRequestData implements Serializable {
    private int place;//<	总车位数
    private int surplus;//<	剩余车位数
    private int fixed;//<	固定车数量
    private int interim;//<	临时车数量
}
