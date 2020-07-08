package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.order.RlpissOrderJzxInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxYyQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice.IQueryJzxDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 集装箱预约时.查询收发货人/查询发到站
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@RestController
@Slf4j
@RequestMapping("/queryJzxData")
public class QueryJzxDataController {
    @Autowired
    IQueryJzxDataService service;

    @CrossOrigin
    @RequestMapping(path = "getShrByFz")
    @ResponseBody
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getShrByFz(@RequestBody BaseRequestData<RlpissOrderJzxInfo> data){
       log.info("=集装箱相关信息模块=根据收货人查询发站"+data.toString());
        return service.getShrByFz(data);
    }

    @CrossOrigin
    @RequestMapping(path = "getDzByFz")
    @ResponseBody
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getDzByFz(@RequestBody BaseRequestData<RlpissOrderJzxInfo> data){
        log.info("=集装箱相关信息模块=根据到站查询发站"+data.toString());
        return service.getDzByFz(data);
    }

    @CrossOrigin
    @RequestMapping(path = "getFzByDZ")
    @ResponseBody
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getFzByDZ(@RequestBody BaseRequestData<RlpissOrderJzxInfo> data){
        log.info("=集装箱相关信息模块=根据发站查询到站"+data.toString());
        return service.getFzByDZ(data);
    }

    @CrossOrigin
    @RequestMapping(path = "getTyrByDz")
    @ResponseBody
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getTyrByDz(@RequestBody BaseRequestData<RlpissOrderJzxInfo> data){
        log.info("=集装箱相关信息模块=根据发货人查询到站"+data.toString());
        return service.getTyrByDz(data);
    }
}
