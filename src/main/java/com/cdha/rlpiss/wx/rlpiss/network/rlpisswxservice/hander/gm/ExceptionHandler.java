package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一的异常处理类
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = GmManagerException.class)
    @ResponseBody
    private JSONObject exceptionHandler(HttpServletRequest request, GmManagerException e) {



        if (!StringUtils.isEmpty(e.getCode())&&e.getCode().equals("dsly")) {
            BaseDSLYReData<String> resultData = new BaseDSLYReData<String>();
            resultData.setReturnCode("-888");
            resultData.setMsg(e.getMessage());
            String str = JSON.toJSONString(resultData);
            return JSONObject.parseObject(str);
        }else {
            BaseResponseData<String> resultData = new BaseResponseData<String>();
            resultData.setCode("-999");
            resultData.setMsg(e.getMessage());
            String str = JSON.toJSONString(resultData);
            return JSONObject.parseObject(str);
        }


    }
}
