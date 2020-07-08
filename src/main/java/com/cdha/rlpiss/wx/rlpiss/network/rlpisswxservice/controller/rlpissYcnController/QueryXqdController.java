package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd.QueryXQDInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd.XqdInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice.IQueryXQDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-17
 */
@RestController
@Slf4j
@RequestMapping("/query")
public class QueryXqdController {

    @Autowired
    IQueryXQDService service;
    /**
     * 发、到货查询
     * @param data  用户信息+发到类型
     * @return 需求单信息
     */
    @CrossOrigin
    @RequestMapping(path = "queryXQD")
    @ResponseBody
    public BaseResponseData<XqdInfo> queryXQD(@RequestBody BaseRequestData<QueryXQDInfo> data) {
        log.info("=查询需求单模块=获取需求单"+data.toString());
        return service.queryXQD(data);
    }

}
