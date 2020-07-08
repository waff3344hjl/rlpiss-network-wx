package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.wxController;

import com.cdha.wechatsub.wxtools.bean.result.WxOAuth2AccessTokenResult;
import com.cdha.wechatsub.wxtools.common.WxConfig;
import com.cdha.wechatsub.wxtools.dao.text.HttpsUtil;
import com.cdha.wechatsub.wxtools.dao.text.UserInfoUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class RedirectController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxConfig wxConfig;


    /**
     * 微信网页授权流程:
     * 1. 用户同意授权,获取 code
     * 2. 通过 code 换取网页授权 access_token
     * 3. 使用获取到的 access_token 和 openid 拉取用户信息
     *
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * @return
     */
    @GetMapping("/url")
    public String wecahtLogin(@RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "state") String state) throws IOException {

        // 1. 用户同意授权,获取code
        logger.info("收到微信重定向跳转.");
        logger.info("用户同意授权,获取code:{} , state:{}", code, state);

        // 2. 通过code换取网页授权access_token
        if (code != null || !(code.equals(""))) {

            String APPID = wxConfig.getAppId();
            String SECRET = wxConfig.getAppSecret();
            String CODE = code;
            String WebAccessToken = "";
            String openId = "";
            String nickName, sex, openid = "";
            String REDIRECT_URI = "http://cdhagzh.easy.echosite.cn/wechat/url";
            String SCOPE = "snsapi_userinfo";

            String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);
            logger.info("第一步:用户授权, get Code URL:{}", getCodeUrl);

            // 替换字符串，获得请求access token URL
            String tokenUrl = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);
            logger.info("第二步:get Access Token URL:{}", tokenUrl);

            // 通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);
            WxOAuth2AccessTokenResult result = WxOAuth2AccessTokenResult.fromJson(response);

            logger.info("请求到的Access Token:{}", result.toString());



            if (null != result) {

                WebAccessToken = result.getAccess_token();
                openId = result.getOpenid();
                logger.info("获取access_token成功!");
                logger.info("WebAccessToken:{} , openId:{}", WebAccessToken, openId);

                // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                logger.info("第三步:获取用户信息的URL:{}", userMessageUrl);

                // 通过https方式请求获得用户信息响应
                String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);
                JsonParser parser1 = new JsonParser();
                JsonObject userMessageJsonObject = (JsonObject) parser1.parse(userMessageResponse);

                logger.info("用户信息:{}", userMessageJsonObject.toString());

                if (userMessageJsonObject != null) {
                    //用户昵称
                    nickName = userMessageJsonObject.get("nickname").getAsString();
                    //用户性别
                    sex = userMessageJsonObject.get("sex").getAsString();
                    sex = (sex.equals("1")) ? "男" : "女";
                    //用户唯一标识
                    openid = userMessageJsonObject.get("openid").getAsString();

                    logger.info("用户昵称:{}", nickName);
                    logger.info("用户性别:{}", sex);
                    logger.info("OpenId:{}", openid);
                }
            }
        }
        return "登录成功";
    }
}
