package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.share.ShareInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.QueryShareAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.QueryShareData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.ShareData;
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
 * 货主分享需求单号给司机
 */
@Service
public class IUserShareService {
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
     * 根据手机号、微信ID判断是否已经注册
     *
     * @param user 用户
     * @return 1
     */
    private boolean isExistPhoneNum(User user) {
        return dao.selectByPhoneNum(user) != null;
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
     * @param data 分享信息
     * @return 是否分享成功
     */
    public BaseResponseData<String> share(BaseRequestData<ShareInfo> data) {
        checkData(data);
        String fwq = data.getRqData().getHz().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return shareByYcn(data, stationInfo);
            } else {
                return shareByOther(data, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }


    private BaseResponseData<String> shareByYcn(BaseRequestData<ShareInfo> data, StationInfo stationInfo) {
        String hzID = null;
        String sjID = null;
        if (isGzh(data.getMethod())) {
            //公众号用户
            hzID = data.getRqData().getHz().getWxopenid();
            sjID = data.getRqData().getSj().getWxopenid();

        } else if (isAPP(data.getMethod())) {
            //APP用户
            hzID = data.getRqData().getHz().getYhid();
            sjID = data.getRqData().getSj().getYhid();
        }
        //到货 0，发货1
        String xqdlx = data.getRqData().getXqdLx();
        String xqdh = data.getRqData().getXdq();

        String rqXML = ShareData.getXml(ycnIP.getWxip(), hzID, sjID, xqdh, xqdlx, stationInfo);
        logger.info(rqXML + "\n" + ycnIP.getWxip());
        String reXML = manager.getResponseByAQPT("", ycnIP.getAddRL_YDFX(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs()))
            return new BaseResponseData<>("-9", StringUtils.isEmpty(reData.getReturnData()) ? reData.getReturnData() : "内网无返回数据", null);


        return new BaseResponseData<>("0", "分享成功", "");
    }

    private BaseResponseData<String> shareByOther(BaseRequestData<ShareInfo> data, StationInfo stationInfo) {
        return null;
    }

    /**
     * 判断参数合法
     *
     * @param data 1
     */
    private void checkData(BaseRequestData<ShareInfo> data) {
        checkMethod(data.getMethod());

        if (data.getRqData().getHz() == null ||
                (StringUtils.isEmpty(data.getRqData().getHz().getWxopenid()) && StringUtils.isEmpty(data.getRqData().getHz().getYhid()))) {
            throw new GmManagerException("参数错误：货主信息不全");
        }
        if (StringUtils.isEmpty(data.getRqData().getHz().getCycz())) {
            throw new GmManagerException("参数错误：用户服务区为空");
        }
        if (data.getRqData().getSj() == null ||
                (StringUtils.isEmpty(data.getRqData().getSj().getYhid()) && StringUtils.isEmpty(data.getRqData().getSj().getWxopenid()))) {
            throw new GmManagerException("参数错误：司机信息不全");
        }

        if (StringUtils.isEmpty(data.getRqData().getXdq())) {
            throw new GmManagerException("参数错误：分享的需求单ID为空");
        }
        if (StringUtils.isEmpty(data.getRqData().getXqdLx())) {
            logger.error(data.toString());
            throw new GmManagerException("参数错误：分享的需求单类型为空");
        }
        if (!isExistPhoneNum(data.getRqData().getHz())) {
            throw new GmManagerException("异常：用户未注册");
        }

    }

    /**
     * @param data 1
     * @return 1
     */
    public BaseResponseData<List<QueryShareAqptReData.ReturnData>> queryShareXQD(BaseRequestData<User> data) {
        checkSelf(data);
        String fwq = data.getRqData().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return queryShareXQDByYcn(data, stationInfo);
            } else {
                return queryShareXQDByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }


    private BaseResponseData<List<QueryShareAqptReData.ReturnData>> queryShareXQDByYcn(BaseRequestData<User> data, StationInfo stationInfo) {
        String openid = "";
        String yhid = "";
        if (isGzh(data.getMethod())) {
            //公众号用户

            openid = data.getRqData().getWxopenid();
        } else if (isAPP(data.getMethod())) {
            //APP用户
            yhid = data.getRqData().getYhid();
        }
        String rqXML = QueryShareData.getXML(openid, yhid, ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getGetRL_YDFX(), rqXML, ycnIP.getUrl());
        QueryShareAqptReData reData = XMLChange.xmlToObject(reXML, QueryShareAqptReData.class);
        logger.info(reData.toString());
        if (!reData.getHead().getFhzbs().equals("0"))
            return new BaseResponseData<>("-99", "内网服务异常："+XMLChange.xmlToObject(reXML,RegisterAQPTReData.class).getReturnData(), null);
        return new BaseResponseData<>("0", "", reData.getReturnData());
    }


    private BaseResponseData<List<QueryShareAqptReData.ReturnData>> queryShareXQDByOther(BaseRequestData<User> data, StationInfo stationInfo) {
        return null;
    }

    private void checkSelf(BaseRequestData<User> data) {
        checkMethod(data.getMethod());
        if (StringUtils.isEmpty(data.getRqData().getCycz())) {
            throw new GmManagerException("参数错误：用户服务区为空");
        }
        if (data.getRqData() == null ||
                (StringUtils.isEmpty(data.getRqData().getWxopenid()) && StringUtils.isEmpty(data.getRqData().getYhid()))) {
            throw new GmManagerException("参数错误：用户信息不全");
        }
        if (!isExistPhoneNum(data.getRqData())) {
            throw new GmManagerException("异常：用户未注册");
        }

    }

    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, logger);
    }
}
