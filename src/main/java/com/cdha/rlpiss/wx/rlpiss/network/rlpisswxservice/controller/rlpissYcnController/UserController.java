package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserRegister;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service.IUserService;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Desc：API层（会员注册、登录、密码找回、个人资料修改）
 * Date：2020-04-14
 * Author：hjl
 */

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IUserService iUserService;


    /**
     * 用户注册
     *
     * @param baseRequestData 请求参数
     * @return String
     */
    @CrossOrigin
    @RequestMapping(path = "register")
    public BaseResponseData<String> register(@RequestBody BaseRequestData<UserRegister> baseRequestData) {

        BaseResponseData<User> yzm = this.getSMSCodByChaCha(new BaseRequestData<String>("1", baseRequestData.getRqData().getSjh()));
        if (yzm.getRsData() == null) throw new GmManagerException("请获取短信短信验证码");
        String fwq = baseRequestData.getRqData().getCycz()==null ? "":baseRequestData.getRqData().getCycz();

        if (IsYcnSys.isYcnUser(fwq)) {//银川南注册
            return iUserService.registerFromYCN(baseRequestData, yzm);
        }else {//其他站注册
            return iUserService.registerFromOther(baseRequestData, yzm);
        }

    }

    /**
     * 用户登录---包含密码登录
     *
     * @param baseRequestData 请求参数
     * @return User
     */
    @CrossOrigin
    @RequestMapping(path = "login")
    public BaseResponseData<User> login(@RequestBody BaseRequestData<User> baseRequestData) {
        return iUserService.login(baseRequestData);
    }

    /**
     * 用户登录 ---- 包含短信登录
     *
     * @param baseRequestData 请求参数
     * @return User
     */
    @CrossOrigin
    @RequestMapping(path = "login2")
    public BaseResponseData<User> login2(@RequestBody BaseRequestData<User> baseRequestData) {
        return iUserService.login2(baseRequestData);
    }


    @CrossOrigin
    @RequestMapping(path = "login3")
    public BaseResponseData<User> login3(@RequestBody BaseRequestData<User> baseRequestData) {
        return iUserService.login3(baseRequestData);
    }

    /**
     * 获取短信验证码
     *
     * @param phone 手机号
     * @return 短信验证码
     */
    @CrossOrigin
    @RequestMapping(path = "getSMSCod")
    public BaseResponseData<User> getSMSCod(@RequestBody BaseRequestData<String> phone) {
        logger.info("getSMSCod__" + phone);
        return iUserService.getSMSCod(phone);
    }


    /**
     * DESC：注销用户
     *
     * @param data 参数：method表示注销方式   rqData表示注销用户信息
     * @return 是否注销成功
     */
    @CrossOrigin
    @RequestMapping(path = "deleteUser")
    public BaseResponseData<String> deleteUser(@RequestBody BaseRequestData<User> data) {
        return iUserService.deleteUser(data);
    }
    @CrossOrigin
    @RequestMapping(path = "upUser")
    public BaseResponseData<String> upUser(@RequestBody BaseRequestData<UserRegister> data){
        return iUserService.upUser(data);
    }


    @CrossOrigin
    @RequestMapping(path = "getDate")
    public BaseResponseData<User> getSMSCodByChaCha(@RequestBody BaseRequestData<String> baseRequestData) {
        return iUserService.getSMSCodByChaCha(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "CachePut")
    public String CachePut(@RequestBody BaseRequestData<Map<String, String>> baseRequestData) {
        return iUserService.CachePut(baseRequestData);
    }

    @CrossOrigin
    @RequestMapping(path = "CacheEvict")
    public String CacheEvict(@RequestBody BaseRequestData<Map<String, String>> baseRequestData) {
        return iUserService.CacheEvict(baseRequestData);
    }
}
