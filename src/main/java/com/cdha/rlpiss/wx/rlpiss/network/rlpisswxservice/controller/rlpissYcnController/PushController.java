package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.wxpush.DSXQDShouliInfoAnptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.wxpush_service.WxPushMsgService;
import com.cdha.wechatsub.wxtools.exception.WxErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@RestController
@Slf4j
@RequestMapping("/getpush")
public class PushController {
    @Autowired
    WxPushMsgService service;

    @CrossOrigin
    @RequestMapping(path = "xqdsl")
    public void pushXQDshouli() {
        try {
            service.pushXQDshouli();
        } catch (WxErrorException e) {
            e.printStackTrace();

        }
    }

    @CrossOrigin
    @RequestMapping(path = "zxwc")
    public void pusxZxwcXx() {
        try {
            service.pusxZxwcXx();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @RequestMapping(path = "yyhz")
    public void pushYySlToHz() throws WxErrorException {
        service.pushYySlToHz();
    }

    @CrossOrigin
    @RequestMapping(path = "yysj")
    public void pushYySlToSj() throws WxErrorException {
        service.pushYySlToSj();
    }

    @CrossOrigin
    @RequestMapping(path = "jfwc")
    public void pushJfwcXx() {
        try {
            service.pushJfwcXx();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @CrossOrigin
    @RequestMapping(path = "jcm")
    public void pushQcJcmXx() throws WxErrorException {
        service.pushQcJcmXx();
    }


    /**
     * 用手机号码，发站电报码获取需求单详情
     * @param czdbm 电报码
     * @param fhrjbsj 发货经办人手机
     * @return 1
     */
    @CrossOrigin
    @RequestMapping(path = "xqdinfo")
    public BaseResponseData<List<DSXQDShouliInfoAnptReData.ReturnData>> getXqdSlInfo(@RequestParam(value = "CZDBM", required = false) String czdbm,
                                                                                     @RequestParam(value = "CZTMS", required = false)String tms,
                                                                                     @RequestParam(value = "FHRJBSJ", required = false) String fhrjbsj,
                                                                                     @RequestParam(value = "YDHS", required = false) String ydhs){
        return service.getXqdSlInfo(czdbm, tms,fhrjbsj,ydhs);
    }
}
