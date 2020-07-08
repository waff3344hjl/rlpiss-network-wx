package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.share.ShareInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.QueryShareAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service.IUserShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分享需求单
 */
@RestController
@Slf4j
@RequestMapping("/share")
public class UserShareController {
    @Autowired
    IUserShareService service;

    /**
     * 货主
     * 分享需求单
     *
     * @param data 需求单
     * @return 是否分享成功
     */
    @CrossOrigin
    @RequestMapping(path = "shareXQD")
    @ResponseBody
    public BaseResponseData<String> share(@RequestBody BaseRequestData<ShareInfo> data) {
        log.info("=分享需求单模块=分享"+data.toString());
        return service.share(data);
    }

    /**
     * 司机
     * 获取货主分享的需求单
     * @param data 货主wxid  +司机wxid+
     * @return 1
     */
    @CrossOrigin
    @RequestMapping(path = "queryShareXQD")
    @ResponseBody
    public BaseResponseData< List<QueryShareAqptReData.ReturnData>> queryShareXQD(@RequestBody BaseRequestData<User> data) {
        log.info("=分享需求单模块=查询分享"+data.toString());
        return service.queryShareXQD(data);
    }
}
