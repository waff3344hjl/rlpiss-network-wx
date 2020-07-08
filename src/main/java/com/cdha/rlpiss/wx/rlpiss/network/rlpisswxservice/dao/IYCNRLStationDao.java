package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-20
 */

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IYCNRLStationDao {
    /**
     * 查询车站
     * @return 车站信息
     */
    List<StationInfo> selectStations();

    /**
     * 根据微信id查找常用车站
     * @param wxopenid 微信ID
     * @return 常用车站
     */
    List<StationInfo> selectCyczs(@Param("wxopenid") String wxopenid);

    /**
     * 根据站名查询车站信息
     * @param stationName 站名
     * @return  车站信息
     */
    StationInfo selectCzByZm(@Param("stationName") String stationName);

    /**
     * 按局代码查询车站信息
     * @param jdm 局代码
     * @return 车站列表
     */
    List<StationInfo> selectCzByJDM(@Param("jdm") String jdm);
}
