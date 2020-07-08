package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxhxQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice.IQueryHuanJzxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 集装箱还箱
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@RestController
@Slf4j
@RequestMapping("/jzxhx")
public class HuanXiangController {

    @Autowired
    IQueryHuanJzxService service;


    @CrossOrigin
    @RequestMapping(path = "getJzxHx")
    public BaseResponseData<List<JzxhxQueryAqptReData.ReturnData>> queryJzxhx(@RequestBody BaseRequestData<User> baseRequestData) {

        return service.queryJzxhx(baseRequestData);
    }
}
