package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark
 * @ClassName: CarParkResponseData
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 10:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 10:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class CarParkResponseData implements Serializable {
    private int code; //<0、成功，其他失败
    private String msg;//<错误信息
    private int takeover;//<0、正常流程（opendoor、led、sound无效）,1、流程接管（opendoor、led、sound生效）软件不再进行后续流程
    private int opendoor;//<0、开闸，1、其他不开闸
    private String[] led;//<对于显示到LED上的每一行信息
    private String sound;//<对于显示到LED上的每一行信息

}
