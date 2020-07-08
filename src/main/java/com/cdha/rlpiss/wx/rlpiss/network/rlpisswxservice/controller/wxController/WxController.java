package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.wxController;


import com.cdha.wechatsub.wxtools.bean.WxXmlMessage;
import com.cdha.wechatsub.wxtools.bean.WxXmlOutTextMessage;
import com.cdha.wechatsub.wxtools.common.WxConsts;
import com.cdha.wechatsub.wxtools.dao.serviceImpl.WxService;
import com.cdha.wechatsub.wxtools.util.xml.XStreamTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/wx")
public class WxController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WxService iService;
    @Autowired
    WxService wxService;


    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) {
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            return echostr;
        }

        return null;
    }


    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String sendMessage = null;
        try {
            // 微信服务器推送过来的是XML格式WxXmlOutTextMessage
            WxXmlMessage receiveMessage = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            String xmlStr = XStreamTransformer.toXml(WxXmlMessage.class, receiveMessage);
            System.out.print("xmlStr" + xmlStr);
            System.out.println("消息：\n " + receiveMessage.toString());
            String fromUser = receiveMessage.getFromUserName();
            String toUser = receiveMessage.getToUserName();
            String msgType = receiveMessage.getMsgType();
            String message = receiveMessage.getContent();
            String event = receiveMessage.getEvent();
            if (WxConsts.CUSTOM_MSG_TEXT.equals(msgType)) {

                WxXmlOutTextMessage text = WxXmlOutTextMessage.TEXT().toUser(fromUser).fromUser(toUser).content(message).build();
                sendMessage = text.toXml();
            } else if (WxConsts.XML_MSG_EVENT.equals(msgType)) {
                String eventType = receiveMessage.getEvent();
                if (WxConsts.EVT_SUBSCRIBE.equals(eventType)) {
                    WxXmlOutTextMessage text = WxXmlOutTextMessage.TEXT().toUser(fromUser).fromUser(toUser).content(
                            "您好，欢迎关注铁路货运站系统微信公众号。实现广大货主司机朋友快捷查询到发货信息、简化进站流程。\n" +
                            "\n" +
                            "请点击‘智慧物流’菜单,进入‘个人中心’点击‘注册’完成个人信息绑定。\n" +
                            "详细使用流程参见‘操作指南’,祝您生活愉快！").build();
                    sendMessage = text.toXml();
                }
            } else if (WxConsts.XML_MSG_IMAGE.equals(msgType)) {
                //百度AI 图像识别
//                BaiDuAIUtil aiUtil = new BaiDuAIUtil();
//                MyUserSfzInfo userSfzInfo = aiUtil.getBaiDuAiIMG(receiveMessage.getPicUrl(), aiConfig.getAppID(), aiConfig.getAPIKey(), aiConfig.getSecretKey());
//                if (!aiUtil.isCheckSFZ(userSfzInfo)) {
//                    //身份证未识别成功
//                    String errBs = aiUtil.getErrBs(userSfzInfo);
//                    WxXmlOutTextMessage text = WxXmlOutTextMessage.TEXT().toUser(fromUser).fromUser(toUser).content("身份证识别：" + errBs).build();
//                    sendMessage = text.toXml();//发送图片地址给用户：测试
//                } else {
//                    //身份证识别成功
//                    String bs = aiUtil.getErrBs(userSfzInfo);
//                    String sfzid = userSfzInfo.getWords_result().get公民身份号码().getWords();
//                    String sfzNmane = userSfzInfo.getWords_result().get姓名().getWords();
//                    WxXmlOutTextMessage text = WxXmlOutTextMessage.TEXT().
//                            toUser(fromUser).
//                            fromUser(toUser).
//                            content("身份证识别：" + bs + "\n"
//                                    + "姓名：" + sfzNmane + "\n"
//                                    + "身份证号码：" + sfzid + "\n").
//                            build();
//                    sendMessage = text.toXml();//发送图片地址给用户：测试
//                }
//                WenZiShiBieInfo  info = aiUtil.getBaiDuAiWz(receiveMessage.getPicUrl(), aiConfig.getAppID(), aiConfig.getAPIKey(), aiConfig.getSecretKey());
//                logger.error(info.toString());
            }
            System.out.println(sendMessage);
            out.print(sendMessage);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            out.close();
        }
    }






}
