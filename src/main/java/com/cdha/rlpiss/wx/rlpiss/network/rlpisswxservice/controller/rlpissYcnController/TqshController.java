package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.TqshInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.TqshTyrAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.TqshTyrFdzAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice.IQueryTqshService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 提前上货
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@RestController
@Slf4j
@RequestMapping("/tqsh")
public class TqshController {

    @Autowired
    IQueryTqshService service;

    @CrossOrigin
    @RequestMapping(path = "getTyrName")
    public BaseResponseData<List<TqshTyrAqptReData.ReturnData>> getTyrName(@RequestBody BaseRequestData<User> baseRequestData) {
       return service.getTyrName(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "getTyrFdz")
    public BaseResponseData<List<TqshTyrFdzAqptReData.ReturnData>> getTyrFdz(@RequestBody BaseRequestData<TqshInfo> baseRequestData) {
        return service.getTyrFdz(baseRequestData);
    }
}
