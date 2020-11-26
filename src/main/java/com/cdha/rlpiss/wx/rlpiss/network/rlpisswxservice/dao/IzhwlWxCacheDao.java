package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.CacheCyczInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlURL;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 智慧物流 查询根URL
 * @Author HJL
 * @Date Created in 2020-06-10
 */
@Repository
public interface IzhwlWxCacheDao {
    /**
     * 插入 用户 常用车站信息
     *
     * @param cyczInfo 常用车站
     * @return 插入标记
     */
    int insertCacheWxCycz(CacheCyczInfo cyczInfo);

    /**
     * 用户  获取 缓存的常用车站信息
     *
     * @param cyczInfo 用户信息---openid
     * @return 常用车站信息
     */
    CacheCyczInfo findCacheWxCycz(CacheCyczInfo cyczInfo);

}
