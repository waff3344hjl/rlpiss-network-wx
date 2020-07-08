package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.order.RlpissOrderJzxInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxYyQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxYyQueryData;
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
 *
 *    已废弃
 *
 *
 *
 * @Description 集装箱预约时，查询集装箱相关信息
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Service
public class IQueryJzxDataService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    YcnIP ycnIP;
    @Autowired
    IYCNRlUserDao dao;
    @Autowired
    RestTemplateManager manager;

    private BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getjzx(BaseRequestData<RlpissOrderJzxInfo> data, String type, String name){
        String method = data.getMethod();
        String fwq = data.getRqData().getUser().getCycz();
        StringBuffer id = new StringBuffer();
        if (ycnIP.getGzh().equals(method)) {
            id.append(data.getRqData().getUser().getWxopenid());
        } else if (ycnIP.getApp().equals(method)) {
            id.append(data.getRqData().getUser().getYhid());
        } else {
            logger.info(data.toString());
            throw new GmManagerException("method is err");
        }
        if (IsYcnSys.isYcnUser(fwq)) {
            //银川南
            return getJzxDataByYcn(data, new String(id),type,name);
        } else {
            //其他
            return getJzxDataByOther(data, new String(id),type,name);
        }
    }

    /**
     * 根据到站查询托运人
     *
     * @param data 到站
     * @return 1
     */
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getTyrByDz(BaseRequestData<RlpissOrderJzxInfo> data) {
        checkData(data);
        return getjzx(data, "tyr",ycnIP.getGettyrByDz());

    }

    private BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getJzxDataByYcn(BaseRequestData<RlpissOrderJzxInfo> data, String id,String type,String method) {
        String rqXML = JzxYyQueryData.getXML(data.getRqData().getStationInfo().getStationDbm(), data.getRqData().getStationInfo().getStationTms(), ycnIP.getWxip());
        String reXML = manager.getResponseByAQPT("", method, rqXML, ycnIP.getUrl());
        JzxYyQueryAqptReData reData = XMLChange.xmlToObject(reXML, JzxYyQueryAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
           return new BaseResponseData<>(reData.getHead().getFhzbs(),"安全平台无数据返回",null);
        }


        return new BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>>("","",reData.getReturnData());
    }

    private BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getJzxDataByOther(BaseRequestData<RlpissOrderJzxInfo> data, String id,String type,String method) {
        return null;
    }

    /**
     * 根据到站查询反正
     *
     * @param data 到站
     * @return 1
     */
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getFzByDZ(BaseRequestData<RlpissOrderJzxInfo> data) {
        checkData(data);
        return getjzx(data, "fz",ycnIP.getGetFzByDz());

    }

    /**
     * 根据发站查询到站
     *
     * @param data 发站
     * @return 1
     */
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getDzByFz(BaseRequestData<RlpissOrderJzxInfo> data) {
        checkData(data);
        return getjzx(data, "dz",ycnIP.getGetDzByFz());
    }

    /**
     * 根据发站查询收货人
     *
     * @param data 1
     * @return 1
     */
    public BaseResponseData<List<JzxYyQueryAqptReData.ReturnData>> getShrByFz(BaseRequestData<RlpissOrderJzxInfo> data) {
        checkData(data);
        return getjzx(data, "shr",ycnIP.getGetShrByFz());
    }

    /**
     * 检查参数格式
     *
     * @param data 1
     */
    private void checkData(BaseRequestData<RlpissOrderJzxInfo> data) {
        if (StringUtils.isEmpty(data.getMethod())) {
            logger.info(data.toString());
            throw new GmManagerException("参数错误:method is null");
        }
        if (!data.getMethod().equals(ycnIP.getGzh()) && !data.getMethod().equals(ycnIP.getApp())) {
            logger.info(data.toString());
            throw new GmManagerException("参数错误:method 不合法");
        }
        if (data.getRqData().getUser() == null || (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid()) && StringUtils.isEmpty(data.getRqData().getUser().getYhid()))) {
            logger.info(data.toString());
            throw new GmManagerException("参数错误:user is null");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            logger.info(data.toString());
            throw new GmManagerException("参数错误:cycz is null");
        }
        if (data.getRqData().getStationInfo() == null || StringUtils.isEmpty(data.getRqData().getStationInfo().getStationTms()) || StringUtils.isEmpty(data.getRqData().getStationInfo().getStationDbm())) {
            logger.info(data.toString());
            throw new GmManagerException("参数错误:StationInfo is null");
        }
        if (!isExistPhoneNum(data.getRqData().getUser())) {
            logger.info(data.toString());
            throw new GmManagerException("用户未注册");
        }

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
}
