package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserRegister;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.CySfhrData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.SfhrQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.order_service.IOrderService;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 经办人管理我的收发货人
 * @Author HJL
 * @Date Created in 2020-05-16
 */
@Service
public class ISFHRService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;

    @Autowired
    IYCNRLStationDao stationDao;

    @Autowired
    IYCNRlUserDao dao;

    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, logger);
    }

    private void checkData(BaseRequestData<UserRegister> rqData) {
        if (rqData.getRqData() == null || StringUtils.isEmpty(rqData.getRqData().getWxopenid())) {
            logger.error(rqData.getRqData().toString());
            throw new GmManagerException("user is null");
        }
        if (StringUtils.isEmpty(rqData.getRqData().getCycz())) {
            throw new GmManagerException("cycz is null");
        }
        if (!isExistPhoneNum(rqData.getRqData())) {
            throw new GmManagerException("用户未注册");
        }
    }
    private void checkSfhr(BaseRequestData<UserRegister> rqData){
        if (rqData.getRqData().getSfhrName() == null || StringUtils.isEmpty(rqData.getRqData().getSfhrName().getSfhrName())) {
            throw new GmManagerException("sfhr is null");
        }
    }

    /**
     * 判断请求来自APP or 公众号
     *
     * @param yylx 参数 method
     * @return T/F
     */
    private boolean isGzh(String yylx) {
        return ycnIP.getGzh().equals(yylx);
    }

    /**
     * 判断请求来自APP or 公众号
     *
     * @param yylx 参数 method
     * @return T/F
     */
    private boolean isAPP(String yylx) {
        return ycnIP.getApp().equals(yylx);
    }

    /**
     * 根据手机号、微信ID判断是否已经注册
     *
     * @param user 用户
     * @return 1
     */
    private boolean isExistPhoneNum(User user) {
        return dao.selectByPhoneNum(user) != null;
    }

    /**
     * 添加收发货人
     *
     * @param rqData 用户信息+收发货人名字
     * @return T/F
     */
    public BaseResponseData<String> addSfhr(BaseRequestData<UserRegister> rqData) {
        logger.info(rqData.getRqData().getCycz());
        checkMe(rqData);
        checkSfhr(rqData);
        String fwq = rqData.getRqData().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "添加收发货人:无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return addSfhrByYcn(rqData, stationInfo);
            } else {
                return addSfhrByOther(rqData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }


    private BaseResponseData<String> addSfhrByYcn(BaseRequestData<UserRegister> rqData, StationInfo stationInfo) {
        String rqXML = CySfhrData.getXML(rqData.getRqData().getWxopenid(),
                rqData.getRqData().getSfhrName().getSfhrName(), "",
                ycnIP.getWxip(),
                stationInfo);
        logger.info("addSfhrByYcn___________"+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getAddSfhr(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            logger.error("添加收发货人失败：" + reData.toString());
            return new BaseResponseData<>(reData.getHead().getFhzbs(), "", null);
        }
        return new BaseResponseData<>("0", "添加成功", null);
    }

    private BaseResponseData<String> addSfhrByOther(BaseRequestData<UserRegister> rqData, StationInfo stationInfo) {
        logger.error("cycz is not ycn");
        return null;
    }

    /**
     * 删除收发货人
     *
     * @param rqData 用户信息+收发货人名字
     * @return T/F
     */
    public BaseResponseData<String> delSfhr(BaseRequestData<UserRegister> rqData) {
        checkMe(rqData);
        checkSfhr(rqData);
        String fwq = rqData.getRqData().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "删除收发货人:无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return delSfhrByYcn(rqData, stationInfo);
            } else {
                return delSfhrByOther(rqData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> delSfhrByYcn(BaseRequestData<UserRegister> rqData, StationInfo stationInfo) {
        String rqXML = CySfhrData.getXML(rqData.getRqData().getWxopenid(),
                rqData.getRqData().getSfhrName().getSfhrName(), "23b5949d5c49414e9500c0678a6af8e3",
                ycnIP.getWxip(),
                stationInfo);
        logger.info("delSfhrByYcn______"+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getDelSfhr(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            logger.error("删除收发货人失败：" + reData.toString());
            return new BaseResponseData<>(reData.getHead().getFhzbs(), "", null);
        }
        return new BaseResponseData<>("0", "删除成功", null);
    }

    private BaseResponseData<String> delSfhrByOther(BaseRequestData<UserRegister> rqData, StationInfo stationInfo) {
        logger.error("cycz is not ycn");
        return null;
    }

    /**
     * 获取经办人对应收发货人
     *
     * @param rqData 用户信息
     * @return 收发货人列表
     */
    public BaseResponseData<List<SfhrQueryAqptReData.ReturnData>> querySfhr(BaseRequestData<UserRegister> rqData) {
        checkMe(rqData);
        String fwq = rqData.getRqData().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "获取经办人对应收发货人:无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return querySfhrByYcn(rqData, stationInfo);
            } else {
                return querySfhrByOther(rqData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<SfhrQueryAqptReData.ReturnData>> querySfhrByYcn(BaseRequestData<UserRegister> rqData, StationInfo stationInfo) {
        String rqXML = CySfhrData.getXML(rqData.getRqData().getWxopenid(),
                "", "",
                ycnIP.getWxip(),
                stationInfo);
        logger.info("querySfhrByYcn____"+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getQuerySfhr(), rqXML, ycnIP.getUrl());
        SfhrQueryAqptReData reData = XMLChange.xmlToObject(reXML, SfhrQueryAqptReData.class);
        if (!"0".equals(reData.getHead().getFHZBS())) {
            logger.error("查询收发货人失败：" + reData.toString());
            return new BaseResponseData<>(reData.getHead().getFHZBS(), "", null);
        }

        return new BaseResponseData<>("0", "查询成功", reData.getReturnData());
    }

    private BaseResponseData<List<SfhrQueryAqptReData.ReturnData>> querySfhrByOther(BaseRequestData<UserRegister> rqData, StationInfo stationInfo) {
        logger.error("cycz is not ycn");
        return null;
    }

    private void checkMe(BaseRequestData<UserRegister> rqData) {
        if (StringUtils.isEmpty(rqData.getMethod())) {
            throw new GmManagerException("method is null");
        }
        checkMethod(rqData.getMethod());
        checkData(rqData);

    }
}
