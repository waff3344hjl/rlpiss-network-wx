package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.TqshInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.TqshQueryData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.TqshTyrAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.TqshTyrFdzAqptReData;
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
 * @Description 提前上货
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@Slf4j
@Service
public class IQueryTqshService {

    @Autowired
    YcnIP ycnIP;


    @Autowired
    RestTemplateManager manager;


    @Autowired
    IYCNRLStationDao stationDao;


    public BaseResponseData<List<TqshTyrAqptReData.ReturnData>> getTyrName(BaseRequestData<User> baseRequestData) {
        log.info("=== 提前上货模块  获取托运人名称 ===" + baseRequestData.toString());
        if (StringUtils.isEmpty(baseRequestData.getMethod())) {
            log.error("=== 提前上货模块  获取托运人名称  ===" + baseRequestData.getMethod());
            throw new GmManagerException(" method is null");
        }
        checkMethod(baseRequestData.getMethod());
        checkDataByGetName(baseRequestData);
        String fwq = baseRequestData.getRqData().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "提前上货模块=获取托运人名称: 无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return getTyrNameByYcn(baseRequestData, stationInfo);
            } else {
                return getTyrNameByOther(baseRequestData, stationInfo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<TqshTyrAqptReData.ReturnData>> getTyrNameByYcn(BaseRequestData<User> baseRequestData, StationInfo stationInfo) {
        String rqXml = TqshQueryData.getXML(baseRequestData.getRqData().getWxopenid(),"",ycnIP.getWxip(),stationInfo);
        log.info("=== 提前上货模块  获取托运人名称 传入内网XML===" +rqXml);
        String reXml = manager.getResponseByAQPT("",ycnIP.getTqshtyrxx(),rqXml,ycnIP.getUrl());
        TqshTyrAqptReData reData = XMLChange.xmlToObject(reXml,TqshTyrAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {

            RegisterAQPTReData errData = XMLChange.xmlToObject(reXml,RegisterAQPTReData.class);
            throw new GmManagerException(errData.getReturnData());
        }
        BaseResponseData<List<TqshTyrAqptReData.ReturnData>> data = new BaseResponseData<>();
        data.setCode("0");
        data.setRsData(reData.getReturnData());
        log.info("=== 提前上货模块  获取托运人名称  返回前台数据 ===" +data.toString());

        return data;

    }

    private BaseResponseData<List<TqshTyrAqptReData.ReturnData>> getTyrNameByOther(BaseRequestData<User> baseRequestData, StationInfo stationInfo) {
        log.error("=== 提前上货模块  获取托运人名称  === cycz is not ycn");
        return null;
    }


    public BaseResponseData<List<TqshTyrFdzAqptReData.ReturnData>> getTyrFdz(BaseRequestData<TqshInfo> baseRequestData) {
        log.info("=== 提前上货模块  获取托运人发到站  ===" + baseRequestData.toString());
        if (StringUtils.isEmpty(baseRequestData.getMethod())) {
            log.error("=== 提前上货模块  获取托运人发到站  ===" + baseRequestData.getMethod());
            throw new GmManagerException(" method is null");
        }
        checkMethod(baseRequestData.getMethod());
        checkDataByTyrFdz(baseRequestData);
        String fwq = baseRequestData.getRqData().getUser().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "提前上货模块 == 获取托运人发到站: 无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return getTyrFdzByYcn(baseRequestData, stationInfo);
            } else {
                return getTyrFdzByOther(baseRequestData, stationInfo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<TqshTyrFdzAqptReData.ReturnData>> getTyrFdzByYcn(BaseRequestData<TqshInfo> baseRequestData, StationInfo stationInfo) {
        String rqXml = TqshQueryData.getXML(baseRequestData.getRqData().getUser().getWxopenid(),baseRequestData.getRqData().getTyrName(),ycnIP.getWxip(),stationInfo);
        String reXml = manager.getResponseByAQPT("",ycnIP.getTqshtyrfdzxx(),rqXml,ycnIP.getUrl());
        TqshTyrFdzAqptReData reData = XMLChange.xmlToObject(reXml,TqshTyrFdzAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {

            RegisterAQPTReData errData = XMLChange.xmlToObject(reXml,RegisterAQPTReData.class);
            throw new GmManagerException(errData.getReturnData());
        }
        BaseResponseData<List<TqshTyrFdzAqptReData.ReturnData>> data = new BaseResponseData<>();
        data.setCode("0");
        data.setRsData(reData.getReturnData());
        log.info("=== 提前上货模块  获取托运人发到站  返回前台数据 ===" +data.toString());

        return data;

    }

    private BaseResponseData<List<TqshTyrFdzAqptReData.ReturnData>> getTyrFdzByOther(BaseRequestData<TqshInfo> baseRequestData, StationInfo stationInfo) {
        log.error("=== 提前上货模块  获取托运人发到站  === cycz is not ycn" );
        return null;
    }

    private void checkDataByGetName(BaseRequestData<User> baseRequestData) {
        if (baseRequestData.getRqData() == null) {
            log.error("=== 提前上货模块  获取托运人名称  ===" + baseRequestData.toString());
            throw new GmManagerException("user is null");
        }
        checkUser(baseRequestData.getRqData());
    }

    private void checkUser(User user) {
        if (StringUtils.isEmpty(user.getCycz())) {
            log.error("=== 提前上货模块  获取托运人名称  ===" + user.toString());
            throw new GmManagerException(" cycz is null");
        }
        if (StringUtils.isEmpty(user.getWxopenid()) && StringUtils.isEmpty(user.getYhid())) {
            log.error("=== 提前上货模块  获取托运人名称  ===" + user.toString());
            throw new GmManagerException(" Wxopenid or yyid is null");
        }
    }


    private void checkDataByTyrFdz(BaseRequestData<TqshInfo> baseRequestData) {
        if (baseRequestData.getRqData() == null) {
            log.error("=== 提前上货模块  获取托运人发到站  ===" + baseRequestData.toString());
            throw new GmManagerException(" data is null");
        }
        if (baseRequestData.getRqData().getUser() == null) {
            log.error("=== 提前上货模块  获取托运人发到站  ===" + baseRequestData.toString());
            throw new GmManagerException(" USER is null");
        }

        if (StringUtils.isEmpty(baseRequestData.getRqData().getTyrName())) {
            log.error("=== 提前上货模块  获取托运人发到站  ===" + baseRequestData.toString());
            throw new GmManagerException(" tyrName is null");
        }
        checkUser(baseRequestData.getRqData().getUser());
    }

    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, log);

    }

}
