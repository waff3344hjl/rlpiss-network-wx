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
public interface IzhwlUrlDao {
    List<ZxwlURL> getAllUrls();

}
