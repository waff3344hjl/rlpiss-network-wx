package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.ShouQuanInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserAndFrend;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.order_service.IOrderService;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 货主添加司机
 * @Author HJL
 * @Date Created in 2020-04-23
 */
@Service
public class IFrendService {


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
     * 根据手机号、微信ID判断是否已经注册
     *
     * @param user 用户
     * @return 1
     */
    private boolean isExistPhoneNum(User user) {
        return dao.selectByPhoneNum(user) != null;
    }

    /**
     * 根据 手机查询司机用户
     *
     * @param baseRequestData 用户手机号
     * @return 用户信息
     */
    public BaseResponseData<User> querySj(BaseRequestData<UserAndFrend> baseRequestData) {
        checkData(baseRequestData);
        try {
            String fwq = baseRequestData.getRqData().getHz().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(baseRequestData.getRqData().getHz().getCycz())) {
                //银川南用户
                return querySjByYcn(baseRequestData, stationInfo);
            } else {
                //其他站用户
                return querySjByOther(baseRequestData, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }

    private BaseResponseData<User> querySjByYcn(BaseRequestData<UserAndFrend> baseRequestData, StationInfo stationInfo) {

        String rqXML = SJQueryData.getXML(baseRequestData.getRqData().getSj(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getGetSjHy(), rqXML, ycnIP.getUrl());
        User sj = LoginAQPTReData.getUser(reXML);
        return new BaseResponseData<>("0", "", sj);
    }

    private BaseResponseData<User> querySjByOther(BaseRequestData<UserAndFrend> baseRequestData, StationInfo stationInfo) {
        return null;
    }

    /**
     * 添加司机好友
     *
     * @param baseRequestData 货主-司机
     * @return T/F
     */
    public BaseResponseData<String> addSj(BaseRequestData<UserAndFrend> baseRequestData) {
        checkData(baseRequestData);
        try {
            String fwq = baseRequestData.getRqData().getHz().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "添加司机：无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(baseRequestData.getRqData().getHz().getCycz())) {
                //银川南用户
                return addSjByYcn(baseRequestData, stationInfo);
            } else {
                return addSjByOther(baseRequestData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }

    private BaseResponseData<String> addSjByYcn(BaseRequestData<UserAndFrend> baseRequestData, StationInfo stationInfo) {
        String rqXML = SjAddData.getXML(baseRequestData.getRqData(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getAddRL_HYHY(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", "添加失败", null);
        }
        return new BaseResponseData<>("0", "添加成功", null);

    }

    private BaseResponseData<String> addSjByOther(BaseRequestData<UserAndFrend> baseRequestData, StationInfo stationInfo) {
        return null;
    }


    /**
     * 判断参数是否合法
     *
     * @param baseRequestData 参数
     */
    private void checkData(BaseRequestData<UserAndFrend> baseRequestData) {
        if (baseRequestData.getRqData() == null || baseRequestData.getRqData().getHz() == null || baseRequestData.getRqData().getSj() == null) {
            throw new GmManagerException("货主查询司机：参数错误");
        }
        if (baseRequestData.getRqData().getHz().getCycz() == null) {
            throw new GmManagerException("货主查询司机：服务区为空");
        }
        if (!isExistPhoneNum(baseRequestData.getRqData().getHz())) {
            //判断货主是否注册
            throw new GmManagerException("用户未注册");
        }
    }

    /**
     * 删除司机好友
     *
     * @param baseRequestData 1
     * @return 1
     */
    public BaseResponseData<String> delSj(BaseRequestData<UserAndFrend> baseRequestData) {
        checkData(baseRequestData);
        try {
            String fwq = baseRequestData.getRqData().getHz().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "删除司机：无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(baseRequestData.getRqData().getHz().getCycz())) {
                //银川南用户
                return delSjByYcn(baseRequestData, stationInfo);
            } else {
                return delSjByOther(baseRequestData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }

    private BaseResponseData<String> delSjByYcn(BaseRequestData<UserAndFrend> baseRequestData, StationInfo stationInfo) {
        String rqXML = SjAddData.getXML(baseRequestData.getRqData(), stationInfo);
        logger.info("=== 删除好友模块 ==="+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getDelRL_HYHY(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", "删除失败", null);
        }
        return new BaseResponseData<>("0", "删除成功", null);
    }

    private BaseResponseData<String> delSjByOther(BaseRequestData<UserAndFrend> baseRequestData, StationInfo stationInfo) {
        return null;
    }

    /**
     * 用户查询自己好友
     *
     * @param baseRequestData 自己用户信息
     * @return 1
     */
    public BaseResponseData<List<User>> queryFrend(BaseRequestData<User> baseRequestData) {
        if (baseRequestData.getRqData() == null || StringUtils.isEmpty(baseRequestData.getRqData().getWxopenid())) {
            throw new GmManagerException("用户查询自己好友：参数错误");
        }
        if (baseRequestData.getRqData().getCycz() == null) {
            throw new GmManagerException("货主查询司机：服务区为空");
        }
        if (!isExistPhoneNum(baseRequestData.getRqData())) {
            //判断货主是否注册
            throw new GmManagerException("用户未注册");
        }
        logger.info(baseRequestData.toString());
        try {
            String fwq = baseRequestData.getRqData().getCycz();
            StationInfo stationInfo = stationDao.selectCzByZm(fwq);
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "查询司机好友：无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(baseRequestData.getRqData().getCycz())) {
                //银川南用户
                return queryFrendByYcn(baseRequestData, stationInfo);
            } else {
                return queryFrendByOther(baseRequestData, stationInfo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }


    private BaseResponseData<List<User>> queryFrendByYcn(BaseRequestData<User> baseRequestData, StationInfo stationInfo) {
        String rqXML = LoginData.getXML(baseRequestData.getRqData().getWxopenid(), ycnIP.getWxip(), stationInfo);
        String reXML = manager.getResponseByAQPT("", ycnIP.getGetRL_HYHY(), rqXML, ycnIP.getUrl());
        LoginAQPTReData data = LoginAQPTReData.getObjByXml(reXML);
        if (!data.getHead().getFhzbs().equals("0")) return new BaseResponseData<>("-9", "查询好友无数据", null);
        List<User> fredns = LoginAQPTReData.getFrends(reXML);

        return new BaseResponseData<>("0", "", fredns);

    }

    private BaseResponseData<List<User>> queryFrendByOther(BaseRequestData<User> baseRequestData, StationInfo stationInfo) {
        return null;
    }

    /**
     * 用户给好友授权
     *
     * @param data 授权信息
     * @return T/F
     */
    public BaseResponseData<String> shouquan(BaseRequestData<ShouQuanInfo> data) {

        checkSq(data);
        try {
            StationInfo stationInfo = stationDao.selectCzByZm(data.getRqData().getUser().getCycz());
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(data.getRqData().getUser().getCycz())) {
                return shouquanByYcn(data, stationInfo);
            } else {
                return shouquanByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }


    private BaseResponseData<String> shouquanByOther(BaseRequestData<ShouQuanInfo> data, StationInfo stationInfo) {
        logger.info("=== 授权模块 ===" + "cycz is not ycn");
        return null;
    }

    private BaseResponseData<String> shouquanByYcn(BaseRequestData<ShouQuanInfo> data, StationInfo stationInfo) {
        String rqXML = ShouQuanData.getXML(
                StringUtils.changNull(data.getRqData().getUser().getWxopenid()),
                StringUtils.changNull(data.getRqData().getUser().getSjh()),
                StringUtils.changNull(data.getRqData().getFrendPhone()),
                ycnIP.getWxip(), stationInfo);
        logger.info("=== 授权模块 ===" + rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getSq(), rqXML, ycnIP.getUrl());
        ShouQuanAqptReData reData = XMLChange.xmlToObject(reXML, ShouQuanAqptReData.class);
        if (!"0".equals(reData.getHead().getFHZBS())) {
            throw new GmManagerException("错误代码：" + XMLChange.xmlToObject(reXML, RegisterAQPTReData.class).getReturnData());
        }
        return new BaseResponseData<>("0", "", null);
    }

    private void checkSq(BaseRequestData<ShouQuanInfo> data) {
        if (StringUtils.isEmpty(data.getMethod())) {
            throw new GmManagerException("method is null");
        }
        checkMethod(data.getMethod());

        if (data.getRqData() == null) {
            throw new GmManagerException("data is null");
        }
        if (data.getRqData().getUser() == null) {
            throw new GmManagerException("user is null");
        }

        if (data.getRqData().getFrendPhone() == null) {
            throw new GmManagerException("授权对象 is null");
        }

        if (StringUtils.isEmpty(data.getRqData().getUser().getCycz())) {
            throw new GmManagerException("参数错误：用户服务区为空");
        }
        if (data.getRqData() == null ||
                (StringUtils.isEmpty(data.getRqData().getUser().getWxopenid()) && StringUtils.isEmpty(data.getRqData().getUser().getYhid()))) {
            throw new GmManagerException("参数错误：用户信息不全");
        }
        if (!isExistPhoneNum(data.getRqData().getUser())) {
            throw new GmManagerException("异常：用户未注册");
        }
    }


    private void checkMethod(String method) {
        IOrderService.checkMethod(method, ycnIP, logger);
    }

    /**
     * 撤销授权
     *
     * @param data 1
     * @return 1
     */
    public BaseResponseData<String> delShouquan(BaseRequestData<ShouQuanInfo> data) {
        checkSq(data);

        try {
            StationInfo stationInfo = stationDao.selectCzByZm(data.getRqData().getUser().getCycz());
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            if (IsYcnSys.isYcnUser(data.getRqData().getUser().getCycz())) {
                return delshouquanByYcn(data, stationInfo);
            } else {
                return delshouquanByOther(data, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }
    }

    private BaseResponseData<String> delshouquanByOther(BaseRequestData<ShouQuanInfo> data, StationInfo stationInfo) {
        logger.info("=== 撤销授权模块 ===" + "cycz is not ycn");
        return null;
    }

    private BaseResponseData<String> delshouquanByYcn(BaseRequestData<ShouQuanInfo> data, StationInfo stationInfo) {
        String rqXML = ShouQuanData.getXML(
                data.getRqData().getUser().getWxopenid(),
                data.getRqData().getUser().getSjh(),
                data.getRqData().getFrendPhone(),
                ycnIP.getWxip(), stationInfo);
        logger.info("=== 撤销授权模块 ===" + rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getDelsq(), rqXML, ycnIP.getUrl());
        ShouQuanAqptReData reData = XMLChange.xmlToObject(reXML, ShouQuanAqptReData.class);
        if (!"0".equals(reData.getHead().getFHZBS())) {
            throw new GmManagerException("撤销授权错误代码：" + XMLChange.xmlToObject(reXML, RegisterAQPTReData.class).getReturnData());
        }
        return new BaseResponseData<>("0", "", null);
    }
}
