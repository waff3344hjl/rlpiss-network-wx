package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserRegister;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.SfhrQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service.ISFHRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 经办人管理常用收发货人
 * @Author HJL
 * @Date Created in 2020-05-16
 */
@RestController
@Slf4j
@RequestMapping("/sfhr")
public class TyrManageController {
    @Autowired
    ISFHRService service;

    /**
     * 添加 收发货人
     *
     * @return T/F
     */
    @CrossOrigin
    @RequestMapping(path = "addSfhr")
    @ResponseBody
    public BaseResponseData<String> addSfhr(@RequestBody BaseRequestData<UserRegister> rqData) {
        log.info("=代理公司模块=添加货主"+rqData.toString());
        return service.addSfhr(rqData);
    }

    /**
     * 删除收发货人
     *
     * @return T/F
     */
    @CrossOrigin
    @RequestMapping(path = "delSfhr")
    @ResponseBody
    public BaseResponseData<String> delSfhr(@RequestBody BaseRequestData<UserRegister> rqData) {
        log.info("=代理公司模块=删除货主"+rqData.toString());
        return service.delSfhr(rqData);
    }

    /**
     * 获取收发货人
     *
     * @return T/F
     */
    @CrossOrigin
    @RequestMapping(path = "querySfhr")
    @ResponseBody
    public BaseResponseData<List<SfhrQueryAqptReData.ReturnData>> querySfhr(@RequestBody BaseRequestData<UserRegister> rqData) {
        log.info("=代理公司模块=查询货主"+rqData.toString());
        return service.querySfhr(rqData);
    }

}
