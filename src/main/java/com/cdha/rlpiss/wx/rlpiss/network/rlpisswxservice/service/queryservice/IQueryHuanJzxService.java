package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxhxQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxhxQueryData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.order_service.IOrderService;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 集装箱还箱
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@Slf4j
@Service
public class IQueryHuanJzxService {

    @Autowired
    YcnIP ycnIP;


    @Autowired
    RestTemplateManager manager;


    @Autowired
    IYCNRLStationDao stationDao;


    public BaseResponseData<List<JzxhxQueryAqptReData.ReturnData>> queryJzxhx(BaseRequestData<User> baseRequestData) {
        log.info("=== 获取集装箱还箱模块 ===" + baseRequestData.toString());
        if (StringUtils.isEmpty(baseRequestData.getMethod())) {
            log.error("=== 获取集装箱还箱模块 ===" + baseRequestData.getMethod());
            throw new GmManagerException(" method is null");
        }
        checkMethod(baseRequestData.getMethod());
        checkData(baseRequestData);

        String fwq = baseRequestData.getRqData().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "获取集装箱还箱模块 无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return queryJzxhxByYcn(baseRequestData, stationInfo);
            } else {
                return queryJzxhxByOther(baseRequestData, stationInfo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }




    }

    private BaseResponseData<List<JzxhxQueryAqptReData.ReturnData>> queryJzxhxByYcn(BaseRequestData<User> baseRequestData, StationInfo stationInfo) {
        String rqXML = JzxhxQueryData.getXML(baseRequestData.getRqData().getWxopenid(),ycnIP.getWxip(),stationInfo);
        log.info("=== 获取集装箱还箱模块  传入内网XML===" +rqXML);
        String reXML = manager.getResponseByAQPT("",ycnIP.getJzxhx(),rqXML,ycnIP.getUrl());

        JzxhxQueryAqptReData reData = XMLChange.xmlToObject(reXML,JzxhxQueryAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            RegisterAQPTReData errData = XMLChange.xmlToObject(reXML,RegisterAQPTReData.class);
            log.error("=== 获取集装箱还箱模块 ===" +errData.getReturnData());
            throw new GmManagerException(errData.getReturnData());
        }
        BaseResponseData<List<JzxhxQueryAqptReData.ReturnData>> data = new BaseResponseData<>();
        data.setCode("0");
        data.setRsData(reData.getReturnData());
        log.info("=== 获取集装箱还箱模块  返回前台数据===" +data.toString());
        return data;

    }

    private BaseResponseData<List<JzxhxQueryAqptReData.ReturnData>> queryJzxhxByOther(BaseRequestData<User> baseRequestData, StationInfo stationInfo) {
          log.error(  "=== 获取集装箱还箱模块 === cycz is not ycn" );
           return null;
    }

    private void checkData(BaseRequestData<User> baseRequestData) {
        if (baseRequestData.getRqData() == null) {
            throw new GmManagerException(" User is null");
        }
        if (StringUtils.isEmpty(baseRequestData .getRqData().getCycz())) {
            throw new GmManagerException(" cycz is null");
        }
        if (StringUtils.isEmpty(baseRequestData .getRqData().getWxopenid()) && StringUtils.isEmpty(baseRequestData .getRqData().getYhid())) {
            throw new GmManagerException(" Wxopenid or yyid is null");
        }

    }

    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, log);
    }
}
