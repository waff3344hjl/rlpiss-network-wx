package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.order.RlpissOrderInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.YuYueQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.order_service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService iOrderService;

    /**
     * desc：提交预约
     *
     * @param data 预约信息
     * @return 是否预约成功
     */
    @CrossOrigin
    @RequestMapping(path = "orderJM")
    @ResponseBody
    public BaseResponseData<String> order(@RequestBody BaseRequestData<RlpissOrderInfo> data) {
        log.info("=预约模块=提交预约："+data.toString());
        return iOrderService.order(data);
    }

    /**
     * desc:获取已注册信息
     *
     * @param data 用户信息
     * @return 预约信息
     */
    @CrossOrigin
    @RequestMapping(path = "getOrder")
    @ResponseBody
    public BaseResponseData<List<YuYueQueryAqptReData.ReturnData>> getOrder(@RequestBody BaseRequestData<User> data) {
        log.info("=预约模块=获取预约："+data.toString());
        return iOrderService.getOrder(data);
    }


    /**
     * desc:撤销预约
     *
     * @param data 预约信息
     * @return 是否撤销成功
     */

    @CrossOrigin
    @RequestMapping(path = "deleteOrder")
    @ResponseBody
    public BaseResponseData<String> deleteOrder(@RequestBody BaseRequestData<RlpissOrderInfo> data) {
        log.info("=预约模块=删除预约："+data.toString());
        return iOrderService.deleteOrder(data);
    }
}
