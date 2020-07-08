package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.CarInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.CarRequest;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.StationRequest;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.CyCzxxQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service.IUsermanageSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 个人信息维护
 * @Author HJL
 * @Date Created in 2020-04-16
 */
@RestController
@Slf4j
@RequestMapping("/manage")
public class UserManageController {

    @Autowired
    IUsermanageSevice service;

    /**
     * DESC:维护常用车辆--更新
     *
     * @param data 常用车辆信息
     * @return 是否更新成功
     */
    @CrossOrigin
    @RequestMapping(path = "updataCar")
    @ResponseBody
    public BaseResponseData<String> updataCar(@RequestBody BaseRequestData<CarRequest> data) {
        log.info("=用户管理模块=添加车牌"+data.toString());
        return service.updataCar(data);
    }

    /**
     * DESC:删除常用车辆
     *
     * @param data 常用车辆
     * @return 是否成功
     */
    @CrossOrigin
    @RequestMapping(path = "deleteCar")
    @ResponseBody
    public BaseResponseData<String> deleteCar(@RequestBody BaseRequestData<CarRequest> data) {
        log.info("=用户管理模块=删除车牌"+data.toString());
        return service.deleteCar(data);
    }

    /**
     * DESC:维护常用车站--更新
     *
     * @param data 常用车站
     * @return 是否更新成功
     */
    @CrossOrigin
    @RequestMapping(path = "addStation")
    @ResponseBody
    public BaseResponseData<String> addStation(@RequestBody BaseRequestData<StationRequest> data) {
        log.info("=用户管理模块=添加车站"+data.toString());
        return service.updataStation(data);
    }


    /**
     * DESC:删除常用车站
     *
     * @param data 常用车站
     * @return 是否成功
     */
    @CrossOrigin
    @RequestMapping(path = "deleteStation")
    @ResponseBody
    public BaseResponseData<String> deleteStation(@RequestBody BaseRequestData<StationRequest> data) {
        log.info("=用户管理模块=删除车站"+data.toString());
        return service.deleteStation(data);
    }

    /**
     * 查询常用车辆
     *
     * @param data 用户信息
     * @return 车辆信息
     */
    @CrossOrigin
    @RequestMapping(path = "queryCarInfo")
    @ResponseBody
    public BaseResponseData<List<CarInfo>> queryCarInfo(@RequestBody BaseRequestData<CarRequest> data) {
        log.info("=用户管理模块=查询常用车辆"+data.toString());
        return service.queryCarInfo(data);
    }

    /**
     * 查询常用站信息
     *
     * @param data 用户信息
     * @return 站信息
     */
    @CrossOrigin
    @RequestMapping(path = "queryStationInfo")
    @ResponseBody
    public BaseResponseData<List<CyCzxxQueryAqptReData.ReturnData>> queryStationInfo(@RequestBody BaseRequestData<StationRequest> data) {
        log.info("=用户管理模块=查询常用车站"+data.toString());
        return service.queryStationInfo(data);
    }

    /**
     * 删除常用车站
     *
     * @param data 1
     * @return 1
     */
    @CrossOrigin
    @RequestMapping(path = "delCyczStationInfo")
    @ResponseBody
    public BaseResponseData<String> delCyczStationInfo(@RequestBody BaseRequestData<StationRequest> data) {
        log.info("=用户管理模块=删除常用车站"+data.toString());
        return service.delCyczStationInfo(data);

    }

    /**
     * 更新用户信息
     *
     * @param data 用户信息
     * @return T/F
     */
    @CrossOrigin
    @RequestMapping(path = "updataUser")
    @ResponseBody

    public BaseResponseData<String> updataUser(@RequestBody BaseRequestData<User> data) {
        return service.updataUser(data);
    }

    /**
     * 设置默认车站
     *
     * @param data 用户信息
     * @return T/F
     */
    @CrossOrigin
    @RequestMapping(path = "setMRCZ")
    @ResponseBody
    public BaseResponseData<String> setMRCZ(@RequestBody BaseRequestData<StationRequest> data) {
        log.info("=用户管理模块=设置默认常用车站"+data.toString());
        return service.setMRCZ(data);
    }


}
