package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissController;


import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.JiGuangPushServiceImpl;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushBean;
import com.cdha.wechatsub.wxtools.modle.ReturnDataSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class JgPushController {
    private final JiGuangPushServiceImpl jiGuangPushService;

    @Autowired
    public JgPushController(JiGuangPushServiceImpl jiGuangPushService) {
        this.jiGuangPushService = jiGuangPushService;
    }

    /**
     * 群推，广播
     *
     * @return 1
     */
    @CrossOrigin
    @RequestMapping("/pushAll")
    public ReturnDataSet pushAll() {
        PushBean pushBean = new PushBean();
        pushBean.setTitle("title");
        pushBean.setAlert("content");
        boolean flag = jiGuangPushService.pushAndroid(pushBean);
        return ReturnDataSet.success(flag);
    }

    @CrossOrigin
    @RequestMapping("/pushAndroid")
    public ReturnDataSet pushAndroid() {
        PushBean pushBean = new PushBean();
        pushBean.setTitle("title_6");
        pushBean.setAlert("content_6");
        boolean flag = jiGuangPushService.pushAndroidByalias(pushBean, "6");
        return ReturnDataSet.success(flag);
    }
}
