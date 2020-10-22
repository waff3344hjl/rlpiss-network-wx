package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkUrl;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlCarParkUrl;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlURL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao
 * @ClassName: IzhwlCarParkDao
 * @brief: 查询停车场系统对应部署地址
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/19 14:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 14:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Repository
public interface IzhwlCarParkDao {
    /**
     * 根据appkey获取URL
     *
     * @param appkey appkey
     * @return URL
     */
    CarParkUrl getIpByAppkey(@Param("appkey") String appkey);


}