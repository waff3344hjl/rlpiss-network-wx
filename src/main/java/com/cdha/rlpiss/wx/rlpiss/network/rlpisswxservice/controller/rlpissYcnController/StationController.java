package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.station_service.IStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 查询车站 （查询已经上过我系统的全部车站 ，以便用户选择）
 * @Author HJL
 * @Date Created in 2020-04-20
 */
@RestController
@Slf4j
@RequestMapping("/station")
public class StationController {

    @Autowired
    IStationService service;



    @CrossOrigin
    @RequestMapping(path = "getAll")
    @ResponseBody
    public BaseResponseData<List<StationInfo>> selectStations() {
        return service.selectStations();
    }

}
