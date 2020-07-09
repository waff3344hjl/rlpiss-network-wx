package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 首页展示
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@Service
public class IShouyeService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;

    @Autowired
    IYCNRLStationDao stationDao;

    /**
     * 根据站名显示首页
     *
     * @param data 车站名
     * @return 首页字符
     */
    public BaseResponseData<Map<String, String>> showShouYe(BaseRequestData<String> data) {
        if (StringUtils.isEmpty(data.getRqData())) {
            throw new GmManagerException("参数错误");
        }
        String czm = data.getRqData();
        if (IsYcnSys.isYcnUser(czm)) {
            //银川南展示首页

            return showShouYeByYCN(czm);
        } else {
            //其他站展示首页

            return showShouYeByOther(czm);
        }
    }


    private BaseResponseData<Map<String, String>> showShouYeByYCN(String czm) {

        try {
            StationInfo stationInfo = stationDao.selectCzByZm(czm);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }


            String rqXml = ShouYeData.getXml(stationInfo);
            String xml = manager.getResponseByAQPT("", ycnIP.getGetSyZsXx(), rqXml, ycnIP.getUrl());
            ShouYeAQPTReData reData = XMLChange.xmlToObject(xml, ShouYeAQPTReData.class);
            if (!reData.getHead().getFhzbs().equals("0")) {
                return new BaseResponseData<>("-11", "访问安全平台失败", null);
            }
            Map<String, String> map = new HashMap<>();
            map.put("blxz", reData.getReturnData().getBlxz());
            map.put("fwzn", reData.getReturnData().getFwzl());
            map.put("jzxz", reData.getReturnData().getJzxz());
            map.put("txz", reData.getReturnData().getTxz());
            return new BaseResponseData<>("0", "", map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }

    private BaseResponseData<Map<String, String>> showShouYeByOther(String czm) {
        return null;
    }


    /**
     * 首页展示----获取停限装命令title
     *
     * @param data 站名
     * @return 1
     */
    public BaseResponseData<List<Map<String, String>>> showTXZ(BaseRequestData<String> data) {
        if (StringUtils.isEmpty(data.getRqData())) {
            throw new GmManagerException("参数错误:服务区站名为空");
        }
        String czm = data.getRqData();
        if (IsYcnSys.isYcnUser(czm)) {
            //银川南停限装查询

            return showTXZByYcn(czm);
        } else {
            //其他站停限装查询

            return showTXZeByOther(czm);
        }
    }

    private BaseResponseData<List<Map<String, String>>> showTXZByYcn(String czm) {
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(czm);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战停限装数据", null);
            }
            String rqXml = ShouYeData.getXml(stationInfo);
            String xml = manager.getResponseByAQPT("", ycnIP.getGetTxzMl(), rqXml, ycnIP.getUrl());
            ShouYeForTxzTitleAqptReData data = XMLChange.xmlToObject(xml, ShouYeForTxzTitleAqptReData.class);
            if (!"0".equals(data.getHead().getFhzbs()) || data.getReturnData() == null)
                return new BaseResponseData<>("-9", "无停限装数据", null);
            List<Map<String, String>> maps = new ArrayList<>();
            for (ShouYeForTxzTitleAqptReData.ReturnData returnData : data.getReturnData()
            ) {
                Map<String, String> map = new HashMap<>();
                map.put("mlid", returnData.getMlid() + "");
                map.put("ml", returnData.getMl() + "");
                maps.add(map);
            }
            return new BaseResponseData<>("0", "ok", maps);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<Map<String, String>>> showTXZeByOther(String czm) {
        return null;
    }

    /**
     * 展示停限装详情
     *
     * @param data 站名
     * @return 1
     */
    public BaseResponseData<List<Map<String, String>>> showTXZInfoByYcn(BaseRequestData<ShouYeForTxzInfoData> data) {
        if (StringUtils.isEmpty(data.getRqData().getCzm())) {
            throw new GmManagerException("参数错误:服务区站名为空");
        }
        if (StringUtils.isEmpty(data.getRqData().getMlid())) {
            throw new GmManagerException("参数错误:停限装命令ID为空");
        }
        String mlid = data.getRqData().getMlid();
        String czm = data.getRqData().getCzm();

        if (IsYcnSys.isYcnUser(czm)) {
            //银川南停限装详情查询

            return showTXZInfosByYcn(czm, mlid);
        } else {
            //其他站停限装详情查询

            return showTXZInfosByOther(czm, mlid);
        }
    }


    private BaseResponseData<List<Map<String, String>>> showTXZInfosByYcn(String czm, String mlid) {
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(czm);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "此战为开通服务", null);
            }
            String rqXML = ShouYeForTxzInfoData.getXML(stationInfo, mlid);
            String reXML = manager.getResponseByAQPT("", ycnIP.getGetTxzMx(), rqXML, ycnIP.getUrl());
            ShouYeForTxzInfoAqptReData data = XMLChange.xmlToObject(reXML,ShouYeForTxzInfoAqptReData.class);
            if (!isCoon(data.getHead().getFhzbs())) return new BaseResponseData<>("-9","无停限装明细数据",null);
                          List<Map<String, String>> maps = new ArrayList<>();
            for (ShouYeForTxzInfoAqptReData.ReturnData re:data.getReturnData()
                 ) {
                Map<String, String> map = new HashMap<>();
                map.put("mlid",re.getMlid());
                map.put("mxid",re.getMxid());
                map.put("txz",re.getTxz());

                maps.add(map);
            }

            return new BaseResponseData<>("0","isok",maps);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }

    private BaseResponseData<List<Map<String, String>>> showTXZInfosByOther(String czm, String mlid) {
        return null;
    }


    private boolean isCoon(String fbs){
        return "0".equals(fbs);
    }
}



