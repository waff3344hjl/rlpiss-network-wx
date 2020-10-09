package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl
 * @ClassName: CacheCyczInfo
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/9 11:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/9 11:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class CacheCyczInfo implements Serializable {
    private String id;
    private String openid;
    private String cztmis;
    private String czdbm;
    private String czmc;
    private String wlUrl;
    private String jsqUrl;
    private String hyzUrl;
}
