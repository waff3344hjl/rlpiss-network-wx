package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd.QueryXQDInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd.XqdInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.GetXqdData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 发、到货查询
 * @Author HJL
 * @Date Created in 2020-04-17
 */

@Service
public class IQueryXQDService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;
    @Autowired
    YcnIP ycnIP;



    @Autowired
    IYCNRLStationDao stationDao;

    /**
     * 发、到货查询
     *
     * @param data 用户信息+发到类型
     * @return 需求单信息
     */
    public BaseResponseData<XqdInfo> queryXQD(BaseRequestData<QueryXQDInfo> data) {
        if (StringUtils.isEmpty(data.getRqData().getType())) {
            logger.error("收发货标记为空");
            throw new GmManagerException("参数错误:收发货标记为空");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getSjh()) ||
                StringUtils.isEmpty(data.getRqData().getUser().getSfhr())) {
            logger.error("经办人手机/收发货人为空");
            throw new GmManagerException("参数错误:经办人手机/收发货人为空");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            logger.error("服务区站名为空");
            throw new GmManagerException("参数错误:服务区站名为空");
        }
        String fwq = data.getRqData().getUser().getCycz();//服务区
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-11", fwq + "未开通该服务", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return queryXqdFromYCN(data, stationInfo);
            } else {
                return queryXqdFromOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }

    /**
     * 银川南 获取需求单信息
     *
     * @param data 用户信息
     * @return 需求单信息
     */
    private BaseResponseData<XqdInfo> queryXqdFromYCN(BaseRequestData<QueryXQDInfo> data, StationInfo stationInfo) {

        String type = data.getRqData().getType();// 收发类型
        String sjh = data.getRqData().getUser().getSjh();//手机号
        String sfhr = data.getRqData().getUser().getSfhr();//收发货人
        String wxopenid =StringUtils.changNull(data.getRqData().getUser().getWxopenid()) ;

        try {
            if (ycnIP.getFhbj().equals(type)) {
                //发货

                String rqXML = GetXqdData.getXml(wxopenid,sjh, "", stationInfo.getStationDbm(), stationInfo.getStationTms(),ycnIP.getWxip());
                logger.info(rqXML);
                String rqMethod = ycnIP.getGetFhXqdxx();
                return getReByYcn(rqXML, rqMethod);
            } else if (ycnIP.getShbj().equals(type)) {

                //收货

                String rqXML = GetXqdData.getXml(wxopenid,"", sjh, stationInfo.getStationDbm(), stationInfo.getStationTms(),ycnIP.getWxip());
                logger.info(rqXML);
                String rqMethod = ycnIP.getGetDhxcxx();
                return getReByYcn(rqXML, rqMethod);
            } else {
                return new BaseResponseData<>("-11", "收发货参数不规范：0收货 1发货", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }

    private BaseResponseData<XqdInfo> getReByYcn(String rqXML, String rqMethod) {
        String xml = manager.getResponseByAQPT("", rqMethod, rqXML, ycnIP.getUrl());
        XqdInfo xqd = XMLChange.xmlToObject(xml,XqdInfo.class);
        if (!"0".equals(xqd.getHead().getFhzbs())) return new BaseResponseData<>("-11","没有对应需求单信息",null);

        return new BaseResponseData<>("0","",xqd);
    }


    /**
     * 其他站 获取需求单信息
     *
     * @param data 用户信息
     * @return 需求单信息
     */
    private BaseResponseData<XqdInfo> queryXqdFromOther(BaseRequestData<QueryXQDInfo> data, StationInfo stationInfo) {
        return null;
    }


}
