package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissDslyController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.dsly_service.ZxWlPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 推送接口
 * @Author HJL
 * @Date Created in 2020/7/17
 */
@RestController
@Slf4j
@RequestMapping("/zxwlpush")
public class ZxWlPushController {
    private final
    ZxWlPushService pushService;

    @Autowired
    public ZxWlPushController(ZxWlPushService pushService) {
        this.pushService = pushService;
    }

    /**
     * 推送新好友添加信息
     */
    @CrossOrigin
    @RequestMapping(path = "pushWhoAddMe")
    public void pushWhoAddMe() {
        pushService.pushWhoAddMe();
    }

    /**
     * 到货通知
     */
    @CrossOrigin
    @RequestMapping(path = "pushArrivalNotice")
    public void pushArrivalNotice() {
        pushService.pushArrivalNotice();
    }

    /**
     * 货物已发通知
     */
    @CrossOrigin
    @RequestMapping(path = "pushDeliveryNotice")
    public void pushDeliveryNotice() {
        pushService.pushDeliveryNotice();
    }

    /**
     * 汽车运输完成通知
     */
    @CrossOrigin
    @RequestMapping(path = "pushCompletionNotice")
    public void pushCompletionNotice() {
        pushService.pushCompletionNotice();
    }

    /**
     * 新任务推送
     */
    @CrossOrigin
    @RequestMapping(path = "pushNewTask")
    public void pushNewTask() {
        pushService.pushNewTask();
    }

}
