package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd.QueryJZXQDInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxQdQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice.IQueryJzxQdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 集装箱清单查询
 * @Author HJL
 * @Date Created in 2020-05-20
 */
@RestController
@Slf4j
@RequestMapping("/jzxQd")
public class JzxQdController {
    @Autowired
    IQueryJzxQdService service;


    @CrossOrigin
    @RequestMapping(path = "queryJzxQd")
    @ResponseBody
    private BaseResponseData<List<JzxQdQueryAqptReData.ReturnData>> getJzxQd(@RequestBody BaseRequestData<QueryJZXQDInfo> rqData) {

        return service.getJzxQd(rqData);
    }
}
