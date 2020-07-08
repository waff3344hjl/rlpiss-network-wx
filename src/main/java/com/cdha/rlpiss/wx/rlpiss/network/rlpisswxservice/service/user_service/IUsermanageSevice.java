package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.CarInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.CarRequest;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.StationRequest;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.order_service.IOrderService;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.wxservice.wxservice.pojo.user.*;
import com.cdha.rlpiss.wx.wxservice.wxservice.pojo.ycn.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户信息维护
 * @Author HJL
 * @Date Created in 2020-04-16
 */
@Service
public class IUsermanageSevice {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;

    @Autowired
    IYCNRLStationDao stationDao;

    @Autowired
    IYCNRlUserDao dao;

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
     * 常用车辆 == 查询
     *
     * @param data 1
     * @return 1
     */
    public BaseResponseData<List<CarInfo>> queryCarInfo(BaseRequestData<CarRequest> data) {

        if (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())) {
            return new BaseResponseData<>("-9", "查询常用车站参数错误:微信ID为空", null);
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            return new BaseResponseData<>("-8", "查询常用车站参数错误:服务区站名为空", null);
        }

        String fwq = data.getRqData().getUser().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return queryCarInfoByYcn(data, stationInfo);
            } else {
                return queryCarInfoByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<List<CarInfo>> queryCarInfoByYcn(BaseRequestData<CarRequest> data, StationInfo stationInfo) {
        String rqXml = LoginData.getXML(data.getRqData().getUser().getWxopenid(), ycnIP.getWxip(), stationInfo);
        String reXml = manager.getResponseByAQPT("", ycnIP.getGetCyClxx(), rqXml, ycnIP.getUrl());
        CarQueryAqptReData reData = XMLChange.xmlToObject(reXml, CarQueryAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-99", "查询常用车辆错误", null);
        }
        List<CarInfo> cars = new ArrayList<>();
        for (CarQueryAqptReData.ReturnData returnData :
                reData.getReturnData()) {
            CarInfo car = new CarInfo();
            car.setCph(returnData.getCph());
            car.setClxxid(returnData.getClxxID());
            car.setSjsjh(StringUtils.changNull(returnData.getSjsjh()));
            car.setCllx(StringUtils.changNull(returnData.getCllx()));
            car.setWlgs(StringUtils.changNull(returnData.getWlgs()));
            car.setZz(StringUtils.changNull(returnData.getZz()));
            car.setSjxm(StringUtils.changNull(returnData.getSjxm()));

            cars.add(car);
        }

        return new BaseResponseData<>("0", "", cars);
    }

    private BaseResponseData<List<CarInfo>> queryCarInfoByOther(BaseRequestData<CarRequest> data, StationInfo stationInfo) {
        return null;
    }

    /**
     * DESC:常用车辆--更新
     *
     * @param data 常用车辆信息
     * @return 是否更新成功
     */
    public BaseResponseData<String> updataCar(BaseRequestData<CarRequest> data) {
        if (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())) {
            return new BaseResponseData<>("-9", "添加常用车站参数错误:微信ID为空", "");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            return new BaseResponseData<>("-8", "添加常用车站参数错误:服务区站名为空", "");
        }


        String fwq = data.getRqData().getUser().getCycz();
        logger.info(data.toString());
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }

            if (IsYcnSys.isYcnUser(fwq)) {
                return upCarInfoByYcn(data, stationInfo);
            } else {
                return upCarInfoByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> upCarInfoByYcn(BaseRequestData<CarRequest> data, StationInfo stationInfo) {
        String rqXml = CarAddData.getXML(data, ycnIP.getWxip(), stationInfo);
        logger.info("添加车辆----" + rqXml);
        String reXml = manager.getResponseByAQPT("", ycnIP.getAddCyClxx(), rqXml, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXml, RegisterAQPTReData.class);
        logger.info(reData.toString());
        if (!"0".equals(reData.getHead().getFhzbs()))
            return new BaseResponseData<>("-99", "添加失败:" + reData.getReturnData(), "");

        return new BaseResponseData<>("0", "添加成功", "");


    }

    private BaseResponseData<String> upCarInfoByOther(BaseRequestData<CarRequest> data, StationInfo stationInfo) {
        return null;
    }


    /**
     * DESC:常用车辆 删除
     *
     * @param data 常用车辆
     * @return 是否成功
     */
    public BaseResponseData<String> deleteCar(BaseRequestData<CarRequest> data) {
        if (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())) {
            return new BaseResponseData<>("-9", "删除常用车站参数错误:微信ID为空", "");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            return new BaseResponseData<>("-8", "删除常用车站参数错误:服务区站名为空", "");

        }

        String fwq = data.getRqData().getUser().getCycz();
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return deleteCarByYcn(data, stationInfo);
            } else {
                return deleteCarByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }

    private BaseResponseData<String> deleteCarByYcn(BaseRequestData<CarRequest> data, StationInfo stationInfo) {
        if (StringUtils.isEmpty(data.getRqData().getCarInfo().getClxxid())) {
            return new BaseResponseData<>("-9", "删除常用车辆信息参数错误：车辆ID为空", "");
        }
        String rqXML = CarDelData.getXML(data.getRqData().getCarInfo().getClxxid(), ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getDelCyClxx(), rqXML, ycnIP.getUrl());
        //返回体与注册返回体类型，所以复用
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-99", reData.getReturnData(), "");
        }
        return new BaseResponseData<>("0", "ok", "");
    }

    private BaseResponseData<String> deleteCarByOther(BaseRequestData<CarRequest> data, StationInfo stationInfo) {
        return null;
    }

    /**
     * 查询常用车站
     *
     * @param data 1
     * @return 1
     */
    public BaseResponseData<List<CyCzxxQueryAqptReData.ReturnData>> queryStationInfo(BaseRequestData<StationRequest> data) {

        checkMethod(data.getMethod());
        if (data.getRqData() == null
                || data.getRqData().getUser() == null
                || StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())) {
            throw new GmManagerException("user is null");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            throw new GmManagerException("cycz is null");
        }

        try {
            StationInfo stationInfo = stationDao.selectCzByZm(data.getRqData().getUser().getCycz());
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "querycycz:无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(data.getRqData().getUser().getCycz())) {
                return queryStationByYcn(data, stationInfo);
            } else {
                return queryStationByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }

    private BaseResponseData<List<CyCzxxQueryAqptReData.ReturnData>> queryStationByYcn(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        String wxopenid = data.getRqData().getUser().getWxopenid();
        String rqXML = LoginData.getXML(wxopenid, ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getGetCyCzxx(), rqXML, ycnIP.getUrl());
        CyCzxxQueryAqptReData reData = XMLChange.xmlToObject(reXML, CyCzxxQueryAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", reData.getReturnData() == null ? "获取常用车站失败：未知错误" : reData.getReturnData().toString(), null);
        }
        return new BaseResponseData<>("0", " ", reData.getReturnData());
    }

    private BaseResponseData<List<CyCzxxQueryAqptReData.ReturnData>> queryStationByOther(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        return null;
    }


    /**
     * DESC:维护常用车站--更新
     *
     * @param data 常用车站
     * @return 是否更新成功
     */
    public BaseResponseData<String> updataStation(BaseRequestData<StationRequest> data) {
        checkMethod(data.getMethod());
        checkRqData(data.getRqData());
        try {

            StationInfo stationInfo = stationDao.selectCzByZm(data.getRqData().getUser().getCycz());
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "addcycz:无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(data.getRqData().getUser().getCycz())) {
                return updataStationByYcn(data, stationInfo);
            } else {
                return updataStationByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }

    private BaseResponseData<String> updataStationByOther(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        logger.error("updataStation : cycz is not ycn ");
        return null;
    }

    private BaseResponseData<String> updataStationByYcn(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        String rqXML = CyCzxxAddData.getXML(data.getRqData().getCyczData(), data.getRqData().getUser(), ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getAddCyCzxx(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", StringUtils.isEmpty(reData.getReturnData()) ? "添加失败：未知错误" : reData.getReturnData(), "");
        }
        return new BaseResponseData<>("0", "", "");
    }

    private void checkRqData(StationRequest data) {
        if (data.getUser() == null) {
            throw new GmManagerException("user is null");
        }
        if (data.getCyczData() == null) {
            throw new GmManagerException("addCyczInfo is null");
        }
        if (StringUtils.isEmpty(data.getUser().getWxopenid()) && StringUtils.isEmpty(data.getUser().getYhid())) {
            throw new GmManagerException("openid or hyid is null");
        }
        if (StringUtils.isEmpty(data.getUser().getCycz())) {
            throw new GmManagerException("cycz is null");
        }
        if (StringUtils.isEmpty(data.getCyczData().getCycztmis())
                || StringUtils.isEmpty(data.getCyczData().getCyczdbm())
                || StringUtils.isEmpty(data.getCyczData().getCyczmc())) {
            throw new GmManagerException("DBM OR TMIS OR CZNAME FOR addCyczInfo is isEmpty");
        }
        if (!isExistPhoneNum(data.getUser())) {
            throw new GmManagerException("用户未注册");
        }
    }

    /**
     * DESC:删除常用车站
     *
     * @param data 常用车站
     * @return 是否成功
     */
    public BaseResponseData<String> deleteStation(BaseRequestData<StationRequest> data) {
        return null;
    }


    public BaseResponseData<String> updataUser(BaseRequestData<User> data) {
        if (data == null || StringUtils.isEmpty(data.getMethod())) {
            throw new GmManagerException("Method is null");
        }
        checkMethod(data.getMethod());
        if (StringUtils.isEmpty(data.getRqData().getWxopenid()) || StringUtils.isEmpty(data.getRqData().getYhid())) {
            throw new GmManagerException("openid or hyid is null");
        }
        if (!isExistPhoneNum(data.getRqData())) {
            throw new GmManagerException("用户未注册");
        }
        if (StringUtils.isEmpty(data.getRqData().getCycz())) {
            throw new GmManagerException("cycz is null");
        }

        try {
            String fwq = data.getRqData().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "更新ueser：无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return updataUserByYcn(data, stationInfo);
            } else {
                return updataUserOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> updataUserByYcn(BaseRequestData<User> data, StationInfo stationInfo) {

        RegisterData registerData = RegisterData.setNewRegisterData(data.getRqData(), stationInfo, ycnIP.getWxip());

        String rqXML = XMLChange.objectToXml(registerData);
        logger.info("=== 更新用户模块 ==="+data.toString());
        logger.info("=== 更新用户模块 ==="+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getUpdateHyxx(), rqXML
                , ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            throw new GmManagerException(StringUtils.isEmpty(reData.getReturnData()) ? "更新失败" : reData.getReturnData());
        }
        return new BaseResponseData<>("0", "更新成功", "");
    }

    private BaseResponseData<String> updataUserOther(BaseRequestData<User> data, StationInfo stationInfo) {
        logger.error("cycz is not ycn");
        return null;
    }


    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, logger);
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
     * 设置默认常用车站
     *
     * @param data 1
     * @return 1
     */
    public BaseResponseData<String> setMRCZ(BaseRequestData<StationRequest> data) {
        checkMethod(data.getMethod());
        checkMrData(data);
        try {
            String fwq = data.getRqData().getUser().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "设置默认常用车站：无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return setMRCZByYcn(data, stationInfo);
            } else {
                return setMRCZOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> setMRCZByYcn(BaseRequestData<StationRequest> data, StationInfo stationInfo) {

        String rqXML = CyCzxxAddData.getXML2(data.getRqData().getCyczData(), data.getRqData().getUser(), ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getSetMrCyCzxx(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", StringUtils.isEmpty(reData.getReturnData()) ? "设置默认车站：未知错误" : reData.getReturnData(), null);
        }
        return new BaseResponseData<>("0", "设置成功", "");
    }

    private BaseResponseData<String> setMRCZOther(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        logger.error("setMRCZOther :设置默认车站 is not ycn");
        return null;
    }

    private void checkMrData(BaseRequestData<StationRequest> data) {
        if (data == null || data.getRqData() == null || data.getRqData().getUser() == null) {
            throw new GmManagerException("user is null");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            throw new GmManagerException("cycz is null");
        }
        if (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid())) {
            throw new GmManagerException("Wxopenid is null");
        }
        if (StringUtils.isEmpty(data.getRqData().getCyczData().getCyczxxid())) {
            throw new GmManagerException("Cyczxxid is null");
        }
        if (!isExistPhoneNum(data.getRqData().getUser())) {
            throw new GmManagerException("用户未注册");
        }
    }

    public BaseResponseData<String> delCyczStationInfo(BaseRequestData<StationRequest> data) {
        checkMethod(data.getMethod());
        checkMrData(data);
        try {
            String fwq = data.getRqData().getUser().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "删除常用车站：无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(fwq)) {
                return delCZByYcn(data, stationInfo);
            } else {
                return delCZOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> delCZByYcn(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        String rqXML = CyCzxxAddData.getXML2(data.getRqData().getCyczData(), data.getRqData().getUser(), ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getDelCyCzxx(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", StringUtils.isEmpty(reData.getReturnData()) ? "删除常用车站：未知错误" : reData.getReturnData(), null);
        }
        return new BaseResponseData<>("0", "删除成功", "");
    }

    private BaseResponseData<String> delCZOther(BaseRequestData<StationRequest> data, StationInfo stationInfo) {
        logger.error("delCZOther :删除常用车站 is not ycn");
        return null;
    }


}
