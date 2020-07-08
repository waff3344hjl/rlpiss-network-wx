package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.queryservice;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd.QueryJZXQDInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxQdQueryAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JzxQdQueryData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 获取集装箱清单
 * @Author HJL
 * @Date Created in 2020-05-20
 */

@Service
public class IQueryJzxQdService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;
    @Autowired
    YcnIP ycnIP;

    @Autowired
    IYCNRlUserDao userDao;

    @Autowired
    IYCNRLStationDao stationDao;

    /**
     * 获取集装箱清单
     *
     * @param rqData 用户信息+ 取送标记
     * @return 装箱清单
     */
    public BaseResponseData<List<JzxQdQueryAqptReData.ReturnData>> getJzxQd(BaseRequestData<QueryJZXQDInfo> rqData) {
        logger.info("=== 获取集装箱清单模块 ===" + rqData.toString());
        if (StringUtils.isEmpty(rqData.getMethod())) {
            logger.error("=== 获取集装箱清单模块 ===" + rqData.getMethod());
            throw new GmManagerException(" method is null");
        }
        checkMethod(rqData.getMethod());
        checkData(rqData);

        String fwq = rqData.getRqData().getUser().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "获取集装箱清单 无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return getJzxQdByYcn(rqData, stationInfo);
            } else {
                return getJzxQdByOther(rqData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<JzxQdQueryAqptReData.ReturnData>> getJzxQdByYcn(BaseRequestData<QueryJZXQDInfo> rqData, StationInfo stationInfo) {
        String yhsjh = rqData.getRqData().getUser().getSjh();
        String yhwxid = rqData.getRqData().getUser().getWxopenid();

//        String sjh = rqData.getRqData().getJzxHzSj();
        String type = rqData.getRqData().getType();
        String sfbj = type.equals(ycnIP.getShbj()) ? "S" : "F";
        String methodToAqpt = type.equals(ycnIP.getShbj()) ? ycnIP.getGetJzxDhQd() : ycnIP.getGetJzxFhQd();
        String rqXML = JzxQdQueryData.getXML(yhsjh, yhwxid, yhsjh, sfbj, ycnIP.getWxip(), stationInfo);
        logger.info("=== 获取集装箱清单模块 ===" + rqXML);
        String reXML = manager.getResponseByAQPT("", methodToAqpt, rqXML, ycnIP.getUrl());
        JzxQdQueryAqptReData reData = XMLChange.xmlToObject(reXML, JzxQdQueryAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            logger.error("=== 获取集装箱清单模块 ===" + reXML);
            throw new GmManagerException("错误代码：" + XMLChange.xmlToObject(reXML, RegisterAQPTReData.class).getReturnData());
        }


        for (JzxQdQueryAqptReData.ReturnData data1 : reData.getReturnData()
        ) {
            String xhs = data1.getJzxxhs();
            List<String> xdList = new ArrayList<>();
            if (xhs.indexOf("/") == -1) {
                //字符串不存在"/"
                logger.info("=== 获取集装箱清单模块 === 一个箱号");
                xdList.add(xhs);
            } else {
                logger.info("=== 获取集装箱清单模块 === 2 个箱号");
                String xh1 = org.apache.commons.lang3.StringUtils.substringBefore(xhs, "/");
                String xh2 = org.apache.commons.lang3.StringUtils.substringAfter(xhs, "/");
                logger.info("=== 获取集装箱清单模块 === 2 个箱号"+xh1);
                logger.info("=== 获取集装箱清单模块 === 2 个箱号"+xh2);
                if (!StringUtils.isEmpty(xh1)) {
                    xdList.add(xh1);
                }
                if (!StringUtils.isEmpty(xh2)) {
                    xdList.add(xh2);
                }

            }
            data1.setJzxList(xdList);
        }

        BaseResponseData<List<JzxQdQueryAqptReData.ReturnData>> ru = new BaseResponseData<>();
        ru.setCode("0");
        ru.setMsg("访问成功");
        ru.setRsData(reData.getReturnData());
        logger.info("=== 获取集装箱清单模块 === " + ru.toString());
        return ru;

    }

    private BaseResponseData<List<JzxQdQueryAqptReData.ReturnData>> getJzxQdByOther(BaseRequestData<QueryJZXQDInfo> rqData, StationInfo stationInfo) {
        logger.info("=== 获取集装箱清单模块 === cycz is not ycn");
        return null;
    }


    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, logger);
    }

    private void checkData(BaseRequestData<QueryJZXQDInfo> rqData) {
        if (rqData.getRqData().getUser() == null || (StringUtils.isEmpty(rqData.getRqData().getUser().getWxopenid()) && StringUtils.isEmpty(rqData.getRqData().getUser().getYhid()))) {
            logger.error("=== 获取集装箱清单模块 ===" + rqData.getRqData());
            throw new GmManagerException(" user is null");
        }
        if (StringUtils.isEmpty(rqData.getRqData().getUser().getCycz())) {
            logger.error("=== 获取集装箱清单模块 ===" + rqData.getRqData());
            throw new GmManagerException(" cycz is null");
        }
        if (StringUtils.isEmpty(rqData.getRqData().getUser().getSjh())) {
            logger.error("=== 获取集装箱清单模块 ===" + rqData.getRqData());
            throw new GmManagerException(" sjh is null");
        }
        if (!isExistPhoneNum(rqData.getRqData().getUser())) {
            logger.error("=== 获取集装箱清单模块 ===" + rqData.getRqData());
            throw new GmManagerException(" 用户未注册 ");
        }

    }

    /**
     * 根据手机号、微信ID判断是否已经注册
     *
     * @param user 用户
     * @return 1
     */
    private boolean isExistPhoneNum(User user) {
        return userDao.selectByPhoneNum(user) != null;
    }

}
