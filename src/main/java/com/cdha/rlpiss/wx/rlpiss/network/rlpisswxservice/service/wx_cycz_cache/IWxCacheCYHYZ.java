package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.wx_cycz_cache;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IzhwlWxCacheDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.CacheCyczInfo;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.wx_cycz_cache
 * @ClassName: IWxCacheCYHYZ
 * @brief: java类作用描述 - 简要说明
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/10/9 11:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/9 11:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Service
@Slf4j
public class IWxCacheCYHYZ {

    private final IzhwlWxCacheDao dao;

    @Autowired
    public IWxCacheCYHYZ(IzhwlWxCacheDao dao) {
        this.dao = dao;
    }

    /**
     * 插入 用户 常用车站信息
     *
     * @param data 常用车站
     * @return 插入标记
     */
    @Transactional
    public BaseDSLYReData<String> cacheCycz(BaseDSLYRqData<CacheCyczInfo> data) {
        BaseDSLYReData<String> result = new BaseDSLYReData<>();
        if (data == null || data.getData() == null || StringUtils.isEmpty(data.getData().getOpenid())) {
            throw new GmManagerException("参数异常");
        }
        CacheCyczInfo info = data.getData();
        try {
            int tag = dao.insertCacheWxCycz(info);
            if (tag > 0) {
                result.setReturnCode("0");
                result.setMsg("缓存设置成功");
                result.setData("缓存设置成功,条数 = " + tag);
            } else {
                throw new GmManagerException("操作数据库失败");
            }

        } catch (Exception e) {
            log.error("插入 用户 常用车站信息  ||  " + e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

        return result;
    }

    /**
     * 用户  获取 缓存的常用车站信息
     *
     * @param data 用户信息---openid
     * @return 常用车站信息
     */
    public BaseDSLYReData<CacheCyczInfo> getCache(BaseDSLYRqData<CacheCyczInfo> data) {
        if (data == null || data.getData() == null || StringUtils.isEmpty(data.getData().getOpenid())) {
            throw new GmManagerException("参数异常");
        }
        try {
            CacheCyczInfo info = data.getData();
            CacheCyczInfo resultInfo = dao.findCacheWxCycz(info);
            if (resultInfo == null) {
                throw new GmManagerException("没有相关缓存信息");
            } else {
                BaseDSLYReData<CacheCyczInfo> result = new BaseDSLYReData<>();
                result.setReturnCode("0");
                result.setData(resultInfo);
                return result;
            }
        } catch (Exception e) {
            log.error(" 用户  获取 缓存的常用车站信息  ||  " + e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }
}
