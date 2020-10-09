package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.CacheCyczInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushBean;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.wx_cycz_cache.IWxCacheCYHYZ;
import com.cdha.wechatsub.wxtools.modle.ReturnDataSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissController
 * @ClassName: WxCacheCYHYZController
 * @brief: java类作用描述 - 微信公众号缓存用户常用车站设置信息
 * @Description: java类作用描述 -  微信公众号缓存用户常用车站设置信息
 * @Author: HUjl
 * @CreateDate: 2020/10/9 11:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/9 11:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/cache")
public class WxCacheCYHYZController {
    private final IWxCacheCYHYZ wxCacheService;

    @Autowired
    public WxCacheCYHYZController(IWxCacheCYHYZ wxCacheService) {
        this.wxCacheService = wxCacheService;
    }

    /**
     * 插入 用户 常用车站信息
     *
     * @param data 常用车站
     * @return 插入标记
     */
    @CrossOrigin
    @RequestMapping(path = "addCache")
    public BaseDSLYReData<String> addCache(@RequestBody BaseDSLYRqData<CacheCyczInfo> data) {
        return wxCacheService.cacheCycz(data);
    }

    /**
     * 用户  获取 缓存的常用车站信息
     *
     * @param data 用户信息---openid
     * @return 常用车站信息
     */
    @CrossOrigin
    @RequestMapping(path = "getCache")
    public BaseDSLYReData<CacheCyczInfo> getCache(@RequestBody BaseDSLYRqData<CacheCyczInfo> data) {
        return wxCacheService.getCache(data);
    }

}
