package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @Description 静态网页访问
 * @Author HJL
 * @Date Created in 2020-04-20
 */


@Controller
@Slf4j
@RequestMapping("/qr_code")
public class QRcodeController {

    @RequestMapping(value = "/push")
    public String test(Model model) throws IOException {
//        推送界面

        model.addAttribute("openId","xxxxxxxxxxxx");
        return "pushxqd";
    }

    @RequestMapping(value = "/jfwc")
    public String jfwc(Model model) throws IOException {
//        交付完成

        model.addAttribute("openId","xxxxxxxxxxxx");
        return "jfwc";
    }

    @RequestMapping(value = "/czzn")
    public String czzn(Model model) throws IOException {
//        操作指南

        model.addAttribute("openId","xxxxxxxxxxxx");
        return "caozuozhinan";
    }

    @RequestMapping(value = "/gywm")
    public String gywm(Model model) throws IOException {
//        关于我们

        model.addAttribute("openId","xxxxxxxxxxxx");
        return "gywm";
    }
}
