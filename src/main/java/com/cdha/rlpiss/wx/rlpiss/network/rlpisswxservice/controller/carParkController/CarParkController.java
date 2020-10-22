package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.carParkController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestEnterOrOutDoorInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkRequestNowInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.carpark.CarParkResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.carpark_service.CarParkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.carParkController
 * @ClassName: CarParkController
 * @brief: 与停车场系统交互的接口
 * @Description: 供停车场系统上传数据的接口
 * @Author: HUjl
 * @CreateDate: 2020/10/19 9:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/19 9:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/pushCarParkInfo")
public class CarParkController {
    private final
    CarParkService service;

    @Autowired
    public CarParkController(CarParkService service) {
        this.service = service;
    }

    /**
     * 1、车辆入场 数据上传
     * @param requestData  上传的数据
     * @return 是否处理成功
     */
    @CrossOrigin
    @RequestMapping(path = "pushEnterDoorTask")
    public CarParkResponseData pushEnterDoorTask(@RequestBody CarParkRequestEnterOrOutDoorInfo requestData) {
        return service.pushEnterDoorTask(requestData);
    }

    /**
     * 1、车辆出门 数据上传
     * @param requestData 上传的数据
     * @return 是否处理成功
     */
    @CrossOrigin
    @RequestMapping(path = "pushOutDoorTask")
    public CarParkResponseData pushOutDoorTask(@RequestBody CarParkRequestEnterOrOutDoorInfo requestData) {
        return service.outDoorTask(requestData);
    }

    /**
     * 车位实时信息上传
     * @param requestData 上传的数据
     * @return 是否处理成功
     */
    @CrossOrigin
    @RequestMapping(path = "pushCarparkInfo")
    public CarParkResponseData pushCarparkInfo(@RequestBody CarParkRequestNowInfo requestData) {
        return service.pushCarparkInfo(requestData);
    }
}
