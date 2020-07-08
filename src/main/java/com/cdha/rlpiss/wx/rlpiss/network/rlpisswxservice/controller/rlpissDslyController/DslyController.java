package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissDslyController;

import com.alibaba.fastjson.JSONObject;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.ZxwlURL;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.dsly_service.DSLYService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 对外接口---转发多式联运
 * @Author HJL
 * @Date Created in 2020-06-01
 */
@RestController
@Slf4j
@RequestMapping("/dsly")
public class DslyController {


    @Autowired
    DSLYService service;



    /**
     *
     * @param rqData
     * @return
     */

    @CrossOrigin
    @RequestMapping(path = "getData")
    public JSONObject getDataByDsly(@RequestBody BaseDSLYRqData<BaseDSLYRqData.BusinessData> rqData) {

        return service.getDataByDsly(rqData);
    }
    @CrossOrigin
    @RequestMapping(path = "getToken")
    public BaseDSLYReData<BaseDSLYReData.TokenGet> getToken(@RequestBody BaseDSLYRqData<BaseDSLYRqData.AppId> rqData) {
        return getTokenGetBaseDSLYReData();
    }

    private BaseDSLYReData<BaseDSLYReData.TokenGet> getTokenGetBaseDSLYReData() {
        BaseDSLYReData.TokenGet info = new BaseDSLYReData.TokenGet();
        info.setAccessToken("123456789");
        info.setRefreshToken("987654321");
        BaseDSLYReData<BaseDSLYReData.TokenGet> token =new BaseDSLYReData<>();
        token.setReturnCode("0");
        token.setMsg("ok");
        token.setData(info);
        return token;
    }

    @CrossOrigin
    @RequestMapping(path = "reToken")
    public BaseDSLYReData<BaseDSLYReData.TokenGet> reToken(@RequestBody BaseDSLYRqData<BaseDSLYRqData.RfToken> rqData) {
        return getTokenGetBaseDSLYReData();
    }

    @CrossOrigin
    @RequestMapping(path = "getAllUrls")
    public List<ZxwlURL> getAllUrls (){
        return service.getAllUrls();
    }

    @CrossOrigin
    @RequestMapping(path = "getYzm")
    public BaseDSLYReData<User> getYzm (@RequestBody BaseDSLYRqData<String> data){
        return service.getYzm(data);
    }
}
