package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.wxController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/")
public class WxMapController {
//    @ApiOperation(value = "获取微信证书MP")
//@ApiImplicitParam(paramType = "header", name = "token", dataType = "String")
@RequestMapping(value = "/MP_verify_FO6FiOuBac176cQa.txt", method = RequestMethod.GET)
public String getMPVerify() throws UnsupportedEncodingException {
return "FO6FiOuBac176cQa";
}
}
