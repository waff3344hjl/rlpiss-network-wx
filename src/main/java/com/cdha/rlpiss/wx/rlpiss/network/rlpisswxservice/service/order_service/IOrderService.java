package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.order_service;


import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.order.RlpissOrderInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 预约
 */

@Service
public class IOrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;


    @Autowired
    IYCNRlUserDao dao;

    @Autowired
    IYCNRLStationDao stationDao;


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


    private void checkMethod(String method) {
        checkMethod(method, ycnIP, logger);
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

    public static void checkMethod(String method, YcnIP ycnIP, Logger logger) {
        if (StringUtils.isEmpty(method)) {
            throw new GmManagerException("参数错误：应用类型为空");
        }
        if (!method.equals(ycnIP.getApp()) &&
                !method.equals(ycnIP.getGzh())) {
            logger.error("错误参数：" + method);
            throw new GmManagerException("参数错误：应用类型参数不符合规定");
        }
    }

    private void checkData(BaseRequestData<RlpissOrderInfo> data) {
        if (data.getRqData().getUser() == null || data.getRqData().getData() == null) {
            throw new GmManagerException("参数错误:user  or  order is null");
        }

        if (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())
                &&
                StringUtils.isEmpty(data.getRqData().getUser().getYhid())) {
            throw new GmManagerException("参数错误：用户信息不完全");
        }
        if (!isExistPhoneNum(data.getRqData().getUser())) {
            throw new GmManagerException("用户未注册");
        }
        if (StringUtils.isEmpty(data.getRqData().getData().getCllx()) ||
                StringUtils.isEmpty(data.getRqData().getData().getJccz()) ||
                StringUtils.isEmpty(data.getRqData().getData().getYyrq())) {
            throw new GmManagerException("预约信息不完整");
        }

    }

    /**
     * desc：提交预约
     *
     * @param data 预约信息
     * @return 是否预约成功
     */
    public BaseResponseData<String> order(BaseRequestData<RlpissOrderInfo> data) {
        logger.info("提交预约____" + data.getRqData().getData().toString());
        checkMethod(data.getMethod() == null ? "" : data.getMethod());
        checkData(data);
        try {
            String fwq = data.getRqData().getUser().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return orderByYcn(data, stationInfo);
            } else {
                return orderByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> orderByYcn(BaseRequestData<RlpissOrderInfo> data, StationInfo stationInfo) {
        String rqXML = YuYueAddData.getXML(data.getRqData().getData(), stationInfo, ycnIP.getWxip());
        logger.info(rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getYyjm(), rqXML, ycnIP.getUrl());
        YuYueAddAqptReData reData = XMLChange.xmlToObject(reXML, YuYueAddAqptReData.class);
        if ("0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("0", "预约成功", "");
        }
        if ("1".equals(reData.getHead().getFhzbs())) {
            StringBuffer cphs = new StringBuffer("车牌：");
            List<YuYueAddAqptReData.ReturnData> ll = reData.getReturnData();
            LinkedHashSet<String> hs = new LinkedHashSet<String>();
            for (YuYueAddAqptReData.ReturnData v : ll
            ) {
                hs.add(v.getCph());
            }
            for (String cp : hs
            ) {
                cphs.append(cp).append(",");
            }
            cphs.append("已预约");
            BaseResponseData<String> re = new BaseResponseData<>("1", "", new String(cphs));
            logger.info(re.toString());
            return re;
        }
        return new BaseResponseData<>("9", "预约失败", "");
    }

    private BaseResponseData<String> orderByOther(BaseRequestData<RlpissOrderInfo> data, StationInfo stationInfo) {
        return null;
    }


    /**
     * desc:获取已预约信息
     *
     * @param data 用户信息
     * @return 预约信息
     */
    public BaseResponseData<List<YuYueQueryAqptReData.ReturnData>> getOrder(BaseRequestData<User> data) {
        checkMethod(data.getMethod());
        checkUser(data);
        logger.info("===getOrderByYcn===参数验证通过");
        try {
            String fwq = data.getRqData().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return getOrderByYcn(data, stationInfo);
            } else {
                return getOrderByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<YuYueQueryAqptReData.ReturnData>> getOrderByYcn(BaseRequestData<User> data, StationInfo stationInfo) {
        StringBuffer idBuf = new StringBuffer();
        if (isGzh(data.getMethod())) {
            idBuf.append(data.getRqData().getWxopenid());
        } else if (isAPP(data.getMethod())) {
            idBuf.append(data.getRqData().getYhid());
        }
        String rqXML = YuYueQueryData.getXML(new String(idBuf), ycnIP.getWxip(), stationInfo);
        logger.info("===getOrderByYcn==="+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getYyjmcx(), rqXML, ycnIP.getUrl());
        YuYueQueryAqptReData reData = XMLChange.xmlToObject(reXML, YuYueQueryAqptReData.class);
        if (!reData.getHead().getFHZBS().equals("0")) {

            return new BaseResponseData<>("-9", XMLChange.xmlToObject(reXML, RegisterAQPTReData.class).getReturnData(), null);
        }
        Map<String, List<YuYueQueryAqptReData.ReturnData>> map = new HashMap<>();
        for (YuYueQueryAqptReData.ReturnData data1 : reData.getReturnData()) {

            if (map.containsKey(data1.getBzid())) {
                //map有发货人组,添加
                map.get(data1.getBzid()).add(data1);
            } else {
                List<YuYueQueryAqptReData.ReturnData> ll = new ArrayList<>();
                ll.add(data1);
                map.put(data1.getBzid(), ll);
            }
        }
        List<YuYueQueryAqptReData.ReturnData> listRe = new ArrayList<>();
        for (String key :
                map.keySet()) {
            List<String> xqds = new ArrayList<>();
            StringBuffer xqdBf = new StringBuffer();
            List<YuYueQueryAqptReData.ReturnData> list = map.get(key);
            YuYueQueryAqptReData.ReturnData dataBZ = list.get(0);
            for (YuYueQueryAqptReData.ReturnData dataBZ2:list
                 ){
                if (!StringUtils.isEmpty(dataBZ2.getXqdh()) ) {
                    xqds.add(dataBZ2.getXqdh());

                    String xqdSX = dataBZ2.getXqdh().substring(dataBZ2.getXqdh().length()-4,dataBZ2.getXqdh().length());
                    xqdBf.append("【...").append(xqdSX).append("】");
                }
            }
            dataBZ.setXqdhs(xqds);
            dataBZ.setXqdh(new String(xqdBf));
            listRe.add(dataBZ);

        }
        return new BaseResponseData<>("0", "", listRe);
    }

    private BaseResponseData<List<YuYueQueryAqptReData.ReturnData>> getOrderByOther(BaseRequestData<User> data, StationInfo stationInfo) {
        return null;
    }

    private void checkUser(BaseRequestData<User> data) {
        if (data.getRqData() == null) {
            logger.info("===getOrderByYcn=== user  or  order is null");
            throw new GmManagerException("参数错误:user  or  order is null");
        }

        if (StringUtils.isEmpty(data.getRqData().getWxopenid())
                &&
                StringUtils.isEmpty(data.getRqData().getYhid())) {
            logger.info("===getOrderByYcn=== 参数错误：用户信息不完全");
            throw new GmManagerException("参数错误：用户信息不完全");
        }
        if (!isExistPhoneNum(data.getRqData())) {
            logger.info("===getOrderByYcn=== 用户未注册");
            throw new GmManagerException("用户未注册");
        }
        if (StringUtils.isEmpty(data.getRqData().getCycz())) {
            logger.info("===getOrderByYcn=== cycz is null");
            throw new GmManagerException("参数错误：cycz is null");
        }
    }


    /**
     * desc:撤销预约
     *
     * @param data 预约信息
     * @return 是否撤销成功
     */
    public BaseResponseData<String> deleteOrder(BaseRequestData<RlpissOrderInfo> data) {
        checkMethod(data.getMethod());
        checkDelData(data);
        logger.info("===deleteOrder=== 参数验证通过");
        try {
            String fwq = data.getRqData().getUser().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "撤销预约:无此站数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return deleteOrderByYcn(data, stationInfo);
            } else {
                return deleteOrderByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }


    private BaseResponseData<String> deleteOrderByYcn(BaseRequestData<RlpissOrderInfo> data, StationInfo stationInfo) {
        String rqXML = YuYueDelData.getXML(data.getRqData().getUser().getWxopenid(), data.getRqData().getDelCph(), ycnIP.getWxip(), stationInfo);
        logger.info("===deleteOrder=== "+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getYyjmzf(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        logger.info("===deleteOrder=== "+reData.toString());
        if (!"0".equals(reData.getHead().getFhzbs()))
            return new BaseResponseData<>("-9", StringUtils.isEmpty(reData.getReturnData())?"撤销失败":reData.getReturnData(), null);
        return new BaseResponseData<>("0", "撤销成功", null);
    }

    private BaseResponseData<String> deleteOrderByOther(BaseRequestData<RlpissOrderInfo> data, StationInfo stationInfo) {
        return null;
    }

    private void checkDelData(BaseRequestData<RlpissOrderInfo> data) {
        if (data.getRqData() == null || data.getRqData().getUser() == null) {
            throw new GmManagerException("撤销预约参数错误:user  or  order is null");
        }

        if (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())
                &&
                StringUtils.isEmpty(data.getRqData().getUser().getYhid())) {
            throw new GmManagerException("撤销预约参数错误：用户信息不完全");
        }
        if (!isExistPhoneNum(data.getRqData().getUser())) {
            throw new GmManagerException("撤销预约：用户未注册");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            throw new GmManagerException("撤销预约参数错误：cycz is null");
        }
        if (StringUtils.isEmpty(data.getRqData().getDelCph())) {
            throw new GmManagerException("撤销预约参数错误：cph is null");
        }
    }
}
