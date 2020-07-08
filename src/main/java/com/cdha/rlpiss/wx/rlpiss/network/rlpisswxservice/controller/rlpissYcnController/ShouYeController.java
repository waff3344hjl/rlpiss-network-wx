package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.ShouYeForTxzInfoData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service.IShouyeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@RestController
@Slf4j
@RequestMapping("/sy")
public class ShouYeController {


    @Autowired
    IShouyeService service;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(path = "show")
    private BaseResponseData<Map<String,String>> showShouYe(@RequestBody BaseRequestData<String> data) {
        log.info("=首页模块=展示首页"+data.toString());
        return service.showShouYe(data);
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(path = "showTxz")
    private BaseResponseData <List<Map<String, String>>> showTXZByYcn(@RequestBody BaseRequestData<String> data) {
        log.info("=首页模块=展示停限装列表"+data.toString());
        return service.showTXZ(data);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(path = "showTxzInfo")
    private BaseResponseData<List<Map<String, String>>> showTXZInfoByYcn(@RequestBody BaseRequestData<ShouYeForTxzInfoData> data) {
        log.info("=首页模块=展示停限装详情"+data.toString());
        return service.showTXZInfoByYcn(data);
    }


}
