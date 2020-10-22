package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlURL;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark
 * @ClassName: CarParkUrl
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/22 10:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/22 10:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class CarParkUrl implements Serializable {
    private String appKey;
    private String secretkey;
    private String dbm;
    private String zm;
    private String tms;
    private String jdm;
    private String wlUrl;
    private String jsqUrl;
    private String hyzUrl;
    private String fubzNo;
}
