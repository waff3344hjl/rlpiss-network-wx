package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.alibaba.fastjson.JSON;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.ShouQuanInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserAndFrend;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service.IFrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 货主添加常用司机（注册司机）
 * @Author HJL
 * @Date Created in 2020-04-23
 */

@RestController
@Slf4j
@RequestMapping("/frend")
public class FrendController {
    @Autowired
    IFrendService service;

    @CrossOrigin
    @RequestMapping(path = "querySj")
    public BaseResponseData<User> querySj(@RequestBody BaseRequestData<UserAndFrend> baseRequestData) {
        log.info("=好友模块=根据手机查询用户信息：" + baseRequestData.toString());
        String str = JSON.toJSONString(baseRequestData);
        log.info("=好友模块=根据手机查询用户信息：" + str);
        return service.querySj(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "addSj")
    public BaseResponseData<String> addSj(@RequestBody BaseRequestData<UserAndFrend> baseRequestData) {
        log.info("=好友模块=添加好友：" + baseRequestData.toString());

        return service.addSj(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "delSj")
    public BaseResponseData<String> delSj(@RequestBody BaseRequestData<UserAndFrend> baseRequestData) {
        log.info("=好友模块=删除好友：" + baseRequestData.toString());

        return service.delSj(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "queryFrend")
    public BaseResponseData<List<User>> queryFrend(@RequestBody BaseRequestData<User> baseRequestData) {
        log.info("=好友模块=获取用户的好友信息：" + baseRequestData.toString());

        return service.queryFrend(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "sq")
    public BaseResponseData<String> shouquan(@RequestBody BaseRequestData<ShouQuanInfo> data) {
        log.info("=好友授权模块=获取用户的好友信息：" + data.toString());
        return service.shouquan(data);
    }

    @CrossOrigin
    @RequestMapping(path = "delsq")
    public BaseResponseData<String> delShouquan(@RequestBody BaseRequestData<ShouQuanInfo> data) {
        log.info("=好友撤销授权模块=获取用户的好友信息：" + data.toString());
        return service.delShouquan(data);
    }

}
