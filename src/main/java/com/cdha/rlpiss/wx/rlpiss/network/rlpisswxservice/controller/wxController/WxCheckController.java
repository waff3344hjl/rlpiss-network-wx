package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.wxController;

import com.cdha.wechatsub.wxtools.dao.serviceImpl.WxService;
import com.cdha.wechatsub.wxtools.exception.WxErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class WxCheckController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WxService wxService;

    @GetMapping(produces = "text/plain;charset=utf-8")
    @RequestMapping("/check")
    @ResponseBody
    public String authGet(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        log.info(request.getRequestURL().toString());
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}]", signature,
                timestamp, nonce);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

//        final WxMpService wxService = WxMpConfiguration.getMpServices().get(appid);
//        if (wxService == null) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
//        }

        if (wxService.checkSignature(signature,timestamp, nonce, echostr)) {
            return echostr;
        }

        return "非法请求";
    }

    @RequestMapping("/token")
    public String getAccessToken() throws WxErrorException {
        return wxService.getAccessToken();
    }

}
