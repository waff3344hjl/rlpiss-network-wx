package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.wxpush_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.UrlConfig;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.wxpush.WxPushData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.wxpush.WxPushDataMsg;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.wxpush.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.DateUtil;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.bean.TemplateSender;
import com.cdha.wechatsub.wxtools.bean.result.TemplateSenderResult;
import com.cdha.wechatsub.wxtools.common.DuanXinYZConfig;
import com.cdha.wechatsub.wxtools.dao.serviceImpl.WxService;
import com.cdha.wechatsub.wxtools.exception.WxErrorException;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhenzi.sms.ZhenziSmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 微信推送
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Service
public class WxPushMsgService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;


    @Autowired
    IYCNRlUserDao dao;

    @Autowired
    IYCNRLStationDao stationDao;

    @Autowired
    WxService wxService;

    @Autowired
    UrlConfig urlConfig;


    @Autowired
    private DuanXinYZConfig yzConfig;


    public List<StationInfo> selectStations(String jdm) {
        try {
            List<StationInfo> datas = stationDao.selectCzByJDM(jdm);//银川南对应局代码
            if (datas != null && datas.size() > 0) {
                return datas;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("selectStations||--" + e.getMessage());
            return null;
        }


    }

    private String getReDataXML(StationInfo cz, String method) {
        String rqXML = PushData.getXML(ycnIP.getWxip(), cz.getStationTms(), cz.getStationDbm());
        logger.info("===  推送模块  === 【" + method + "】" + "\n" + rqXML);
        return manager.getResponseByAQPT("", method, rqXML, ycnIP.getUrl());
    }

    /**
     * 需求单受理完成
     */
    public void pushXQDshouli() throws WxErrorException {

        List<StationInfo> stationInfos = selectStations("00013");//银川南对应局代码
        if (stationInfos == null || stationInfos.size() == 0) return;
        for (StationInfo cz : stationInfos
        ) {
            String reXML = getReDataXML(cz, ycnIP.getPushxqdsl());
            PushDsSlReData reData = XMLChange.xmlToObject(reXML, PushDsSlReData.class);
            if ("0".equals(reData.getHead().getFHZBS()) && reData.getReturnData() != null && reData.getReturnData().size() > 0) {
                Map<String, List<PushDsSlReData.ReturnData>> map = new HashMap<>();
                for (PushDsSlReData.ReturnData data : reData.getReturnData()) {

                    if (map.containsKey(data.getWxopenid())) {
                        //map有发货人组,添加
                        map.get(data.getWxopenid()).add(data);
                    } else {
                        List<PushDsSlReData.ReturnData> ll = new ArrayList<>();
                        ll.add(data);
                        map.put(data.getWxopenid(), ll);
                    }
                }


                for (String key :
                        map.keySet()) {
                    pushXqdToUser(key, map.get(key));
                }
            }
        }
    }

    private void pushXqdToUser(String wxopenid, List<PushDsSlReData.ReturnData> list) throws WxErrorException {
        logger.info("________" + list.toString());
        logger.info("________" + wxopenid);
        String fhjbrsjh = list.get(0).getFhjbrsj();
        String czdbm = list.get(0).getFzdbm();
        String cztms = list.get(0).getFztms();

        String yhd = list.get(0).getYdydhoo();
        StringBuffer stringBuffer = new StringBuffer();
        for (PushDsSlReData.ReturnData xqd : list
        ) {
            stringBuffer.append(xqd.getYdydhoo()).append(",");
        }
        String ydhs = new String(stringBuffer);
        int le = list.size();
        WxPushDataMsg msgInfo = new WxPushDataMsg();
        msgInfo.setFirst(new WxPushData("您的需求单已受理", ""));//标题。。

        msgInfo.setKeyword1(new WxPushData(yhd, "#173177"));
        msgInfo.setKeyword2(new WxPushData(le + "", "#173177"));

        msgInfo.setRemark(new WxPushData("", ""));//末尾详情


        String mbId = ycnIP.getMbidxqdsl();
        String webType = ycnIP.getMbidxqdsltype();
        String date = DateUtil.getNowTime();
        String mbUrl = urlConfig.getMUrl() + "qr_code/push" + "?czdbm=" + czdbm + "&cztms=" + cztms + "&fhrjbsj=" + fhjbrsjh + "&ydhs=" + ydhs + "&type=" + webType + "&date=" + date;
        logger.info(mbUrl);
        wxPushMsgToUser(wxopenid, msgInfo, mbId, mbUrl);
    }

    private void wxPushMsgToUser(String wxopenid, WxPushDataMsg msgInfo, String wxMBiD, String wxMbUrl) throws WxErrorException {

        TemplateSender sender = new TemplateSender();
        sender.setTemplate_id(wxMBiD);
        sender.setTouser(wxopenid);
        sender.setData(msgInfo);
        sender.setUrl(wxMbUrl);//设置模版信息的超链接

        TemplateSenderResult result = wxService.templateSend(sender);

    }

    /**
     * 用手机号码，发站电报码获取需求单详情
     *
     * @param czdbm   电报码
     * @param fhrjbsj 发货经办人手机
     * @return 1
     */
    public BaseResponseData<List<DSXQDShouliInfoAnptReData.ReturnData>> getXqdSlInfo(String czdbm, String tms,
                                                                                     String fhrjbsj, String ydhs) {
        logger.info("~~~~" + ydhs);
        String rqXML = DSXQDShouliInfoData.getXML(ycnIP.getWxip(), czdbm, tms, fhrjbsj, ydhs);
        logger.info("~~~~" + rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getPushgetxqd(), rqXML, ycnIP.getUrl());
        DSXQDShouliInfoAnptReData reData = XMLChange.xmlToObject(reXML, DSXQDShouliInfoAnptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            logger.error(reData.getReturnData() == null ? "" : reData.getReturnData().toString());
            new BaseResponseData<>("-1", "无数据", null);
        }
        return new BaseResponseData<>("0", "", reData.getReturnData());
    }

    /**
     * 装卸车完成
     */
    public void pusxZxwcXx() throws WxErrorException {

        List<StationInfo> stationInfos = selectStations("00013");//银川南对应局代码
        if (stationInfos == null || stationInfos.size() == 0) return;
        for (StationInfo cz : stationInfos
        ) {
            String reXML = getReDataXML(cz, ycnIP.getPushzxcwc());
            PushZxWcReData reData = XMLChange.xmlToObject(reXML, PushZxWcReData.class);
            logger.info("ZXWC-DATA = " + reData.toString());
            if ("0".equals(reData.getHead().getFHZBS()) && reData.getReturnData() != null && reData.getReturnData().size() > 0) {
                for (PushZxWcReData.ReturnData data : reData.getReturnData()
                ) {
                    pushZxwcToUser(data);
                }
            }
        }
    }

    private void pushZxwcToUser(PushZxWcReData.ReturnData dataZ) throws WxErrorException {

        String zxlx = dataZ.getZxbz();//装卸标记
        String pjid = dataZ.getPjid() + "";//需求单号
        String ch = "" + dataZ.getCh();//车号
        String fdz = dataZ.getFzmc() + "-" + dataZ.getDzmc();//发到站
        String zxkssj = dataZ.getZxkssj() + "";//装卸开始时间
        String zxjssj = dataZ.getZxjssj() + "";//装卸结束时间
        String fztms = dataZ.getFztms();
        String dztms = dataZ.getDztms();
        String tms = "Z".equals(zxlx) ? fztms : dztms;
        String czdbm = "";
        String cztms = "";
        String fz = dataZ.getFzmc();
        String dz = dataZ.getDzmc();
        String pm = dataZ.getPm();
        String hz = dataZ.getHz();

        String webType = ycnIP.getMbidzxwctype();
        String date = DateUtil.getNowTime();

        StationInfo stationInfo = stationDao.selectCzByZm(tms);//根据TMS查询站信息
        if (stationInfo != null) {
            czdbm = stationInfo.getStationDbm();
            cztms = stationInfo.getStationTms();
        }


        WxPushDataMsg msg = new WxPushDataMsg();
        msg.setFirst(new WxPushData("Z".equals(zxlx) ? "您的货物已装车完成" : "您的货物已到站卸车完成", ""));

        msg.setKeyword1(new WxPushData(pjid, "#173177"));
        msg.setKeyword2(new WxPushData(ch, "#173177"));
        msg.setKeyword3(new WxPushData(fdz, "#173177"));
        msg.setKeyword4(new WxPushData(zxkssj, "#173177"));
        msg.setKeyword5(new WxPushData(zxjssj, "#173177"));

        msg.setRemark(new WxPushData("", ""));//末尾详情
        String url = urlConfig.getMUrl() + "qr_code/push" + "?ch=" + ch
                + "&pjid=" + pjid
                + "&fz=" + fz
                + "&dz=" + dz
                + "&pm=" + pm
                + "&hz=" + hz
                + "&zxkssj=" + zxkssj
                + "&zxjssj=" + zxjssj
                + "&zxlx=" + zxlx
                + "&tms=" + cztms
                + "&dbm=" + czdbm
                + "&type=" + webType + "&date=" + date;
        logger.info(url);
        for (PushZxWcReData.WXopenids openids : dataZ.getWxopenids()
        ) {
            wxPushMsgToUser(openids.getWxopenid(), msg, ycnIP.getMbidzxwc(), url);
        }

    }

    /**
     * 预约受理 -推送到货主
     */
    public void pushYySlToHz() throws WxErrorException {
        List<StationInfo> stationInfos = selectStations("00013");//银川南对应局代码
        if (stationInfos == null || stationInfos.size() == 0) return;
        for (StationInfo cz : stationInfos
        ) {
            String reXML = getReDataXML(cz, ycnIP.getPushyuyuetohz());

            PushYyToHzReData reData = XMLChange.xmlToObject(reXML, PushYyToHzReData.class);
            if ("0".equals(reData.getHead().getFHZBS()) && reData.getReturnData() != null && reData.getReturnData().size() > 0) {
                for (PushYyToHzReData.ReturnData data : fenpeiBz(reData)
                ) {
                    pushYyToHzUser(data);
                }
            }
        }

    }

    private void pushYyToHzUser(PushYyToHzReData.ReturnData data) throws WxErrorException {
        StringBuffer hqhwBf = new StringBuffer();
        if (StringUtils.isEmpty(data.getYyhqmc())
                &&
                StringUtils.isEmpty(data.getXqdh())) {
            //需求单号为空 货位为空 ---提前送
            hqhwBf.append("提前送货-无货位");
        } else if (StringUtils.isEmpty(data.getYyhqmc())
                &&
                !StringUtils.isEmpty(data.getXqdh())) {
            //需求单号不为空 货位为空  ---视为集装箱货位
            hqhwBf.append("集装箱货位");
        } else if (!StringUtils.isEmpty(data.getXqdh())
                &&
                !StringUtils.isEmpty(data.getYyhqmc())) {
            //需求单号不为空 货位不为空  ---整车预约
            hqhwBf.append(data.getYyhqmc());
        } else {
            hqhwBf.append("未知货位");
        }

        WxPushDataMsg msg = new WxPushDataMsg();
        msg.setFirst(new WxPushData("您的进门预约已受理", ""));

        msg.setKeyword1(new WxPushData(data.getCph(), "#173177"));//车牌号
        msg.setKeyword2(new WxPushData(StringUtils.isEmpty(data.getXqdh()) ? "提前送货" : data.getXqdh(), "#173177"));//单号
        msg.setKeyword3(new WxPushData(data.getYyrq(), "#173177"));//预约日期
        msg.setKeyword4(new WxPushData(new String(hqhwBf), "#173177"));//货区货位
        msg.setKeyword5(new WxPushData(StringUtils.isEmpty(data.getShr()) ? "未知" : data.getShr() + "-" + data.getFhr(), "#173177"));//收货人-发货人
        msg.setRemark(new WxPushData("", ""));//末尾详情

        List<PushYyToHzReData.WXopenids> wXopenids = data.getWxopenids();
        for (PushYyToHzReData.WXopenids openid : wXopenids
        ) {
            wxPushMsgToUser(openid.getWxopenid(), msg, ycnIP.getMbidyytohz(), "");
        }

    }


    /**
     * 预约受理 -推送到司机
     */
    public void pushYySlToSj() throws WxErrorException {

        List<StationInfo> stationInfos = selectStations("00013");//银川南对应局代码
        if (stationInfos == null || stationInfos.size() == 0) return;
        for (StationInfo cz : stationInfos
        ) {
            String reXML = getReDataXML(cz, ycnIP.getPushyuyuetosj());
            PushYyToSjReData reData = XMLChange.xmlToObject(reXML, PushYyToSjReData.class);
            if ("0".equals(reData.getHead().getFHZBS()) && reData.getReturnData() != null && reData.getReturnData().size() > 0) {
                for (PushYyToSjReData.ReturnData data : fenpeiBzSj(reData)
                ) {
                    pushYyToSjUser(data);
                }
            }
        }

    }

    private void pushYyToSjUser(PushYyToSjReData.ReturnData data) throws WxErrorException {
        StringBuffer hqhwBf = new StringBuffer();
        if (StringUtils.isEmpty(data.getYyhqmc())
                &&
                StringUtils.isEmpty(data.getXqdh())) {
            //需求单号为空 货位为空 ---提前送
            hqhwBf.append("提前送货-无货位");
        } else if (StringUtils.isEmpty(data.getYyhqmc())
                &&
                !StringUtils.isEmpty(data.getXqdh())) {
            //需求单号不为空 货位为空  ---视为集装箱货位
            hqhwBf.append("集装箱货位");
        } else if (!StringUtils.isEmpty(data.getXqdh())
                &&
                !StringUtils.isEmpty(data.getYyhqmc())) {
            //需求单号不为空 货位不为空  ---整车预约
            hqhwBf.append(data.getYyhqmc());
        } else {
            hqhwBf.append("未知货位");
        }

        WxPushDataMsg msg = new WxPushDataMsg();
        msg.setFirst(new WxPushData("您的进门预约已受理", ""));

        msg.setKeyword1(new WxPushData(data.getCph(), "#173177"));//车牌号
        msg.setKeyword2(new WxPushData(StringUtils.isEmpty(data.getXqdh()) ? "提前送货" : data.getXqdh(), "#173177"));//单号
        msg.setKeyword3(new WxPushData(data.getYyrq(), "#173177"));//预约日期
        msg.setKeyword4(new WxPushData(new String(hqhwBf), "#173177"));//货区货位
        msg.setKeyword5(new WxPushData(StringUtils.isEmpty(data.getShr()) ? "未知" : data.getShr() + "-" + data.getFhr(), "#173177"));//收货人
        msg.setRemark(new WxPushData("", ""));//末尾详情

        List<PushYyToSjReData.WXopenids> wXopenids = data.getWxopenids();
        for (PushYyToSjReData.WXopenids openid : wXopenids
        ) {
            wxPushMsgToUser(openid.getWxopenid(), msg, ycnIP.getMbidyytosj(), "");
        }


        dxPushMsgToSj(data);
    }

    private Map<String, Object> getParams(String phone,String cph) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("number", phone);
        params.put("templateId", "83");
        String[] templateParams = new String[1];
        templateParams[0] = cph;
        params.put("templateParams", templateParams);

        return params;
    }
    /**
     * 推送短信至手机
     * @param data y 用户
     */

    private void dxPushMsgToSj(PushYyToSjReData.ReturnData data) {

        //生成6位验证码
//        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //构造client
        ZhenziSmsClient client = new ZhenziSmsClient(yzConfig.getApiurl(), yzConfig.getAppID(), yzConfig.getAppSecret());

        try {
            //构造短信参数

            //发送client
            if (StringUtils.isEmpty(data.getYysjsj()) ||StringUtils.isEmpty(data.getCph())) {
                return;
            }
            String result = client.send(getParams(data.getYysjsj(),data.getCph()));
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(result);
            System.out.println("jsonObject_" + jsonObject);
            if (jsonObject.get("code").getAsInt() != 0) {
                //短信平台返回：发送失败
                logger.info("短信发送失败");
            } else {
              logger.info("短信发送成功");

            }

        } catch (Exception e) {
//            e.printStackTrace();
            throw new GmManagerException(e.getMessage());
        }
    }

    /**
     * 交付完成推送
     */
    public void pushJfwcXx() throws WxErrorException {
        List<StationInfo> stationInfos = selectStations("00013");//银川南对应局代码
        if (stationInfos == null || stationInfos.size() == 0) return;
        for (StationInfo cz : stationInfos
        ) {
            String reXML = getReDataXML(cz, ycnIP.getPushjfwc());
            PushJfwcReData reData = XMLChange.xmlToObject(reXML, PushJfwcReData.class);
            if ("0".equals(reData.getHead().getFhzbs()) && reData.getReturnData() != null && reData.getReturnData().size() > 0) {
                for (PushJfwcReData.ReturnData data : reData.getReturnData()
                ) {
                    pushJfwcToUser(data);
                }
            }
        }
    }

    private void pushJfwcToUser(PushJfwcReData.ReturnData data) throws WxErrorException {

        WxPushDataMsg msg = new WxPushDataMsg();
        msg.setFirst(new WxPushData("您的货物已交付", ""));//设置标题
        msg.setKeyword1(new WxPushData(data.getDjbh() == null ? "" : data.getDjbh(), "#173177"));//设置需求号
        msg.setKeyword2(new WxPushData(data.getPmmc() == null ? "" : data.getPmmc(), "#173177"));//设置品名
        msg.setKeyword3(new WxPushData(data.getHqhwmc() == null ? "" : data.getHqhwmc(), "#173177"));//设置货区货位
        String fz = data.getFzmc() == null ? "" : data.getFzmc();
        String dz = data.getDzmc() == null ? "" : data.getDzmc();
        msg.setKeyword4(new WxPushData(fz + "-" + dz, "#173177"));//设置发到站
        msg.setKeyword5(new WxPushData(data.getByjssj() == null ? "" : data.getByjssj(), "#173177"));//设置交付时间--搬运结束时间
        msg.setRemark(new WxPushData("", "#173177"));//设置备注

        String mbUrl = urlConfig.getMUrl() + "qr_code/jfwc" + "?fzmc=" + data.getFzmc() + "&dzmc=" + data.getDzmc() +
                "&djbh=" + data.getDjbh() + "&blzdbm=" + data.getBlzdbm() +
                "&blzmc=" + data.getBlzmc() + "&blztms=" + data.getBlztms() +
                "&byjssj=" + data.getByjssj() + "&bykssj=" + data.getBykssj() +
                "&hqhwmc=" + data.getHqhwmc() + "&pmmc=" + data.getPmmc();

        for (PushJfwcReData.WXopenids wXopenids : data.getWxopenids()
        ) {
            wxPushMsgToUser(wXopenids.getWxopenid(), msg, ycnIP.getMbidjfwc(), mbUrl);
        }
    }

    /**
     * 汽车进出门信息推送
     */
    public void pushQcJcmXx() throws WxErrorException {
        List<StationInfo> stationInfos = selectStations("00013");//银川南对应局代码
        if (stationInfos == null || stationInfos.size() == 0) return;
        for (StationInfo cz : stationInfos
        ) {
            String reXML = getReDataXML(cz, ycnIP.getPushjcm());
            PushQcJcmReData reData = XMLChange.xmlToObject(reXML, PushQcJcmReData.class);
            if ("0".equals(reData.getHead().getFHZBS()) && reData.getReturnData() != null && reData.getReturnData().size() > 0) {
                for (PushQcJcmReData.ReturnData data : reData.getReturnData()
                ) {
                    pushQcJcmToUser(data);
                }
            }
        }
    }

    private void pushQcJcmToUser(PushQcJcmReData.ReturnData data) throws WxErrorException {
        if ("".equals(data.getJcmbj())) return;
        String jcmbj = data.getJcmbj();
        logger.info("jcmbj__" + jcmbj);
        logger.info("推送进出门:" + data.toString());
        String bt = "";
        String mbid = "";
        String sj = "";
        String webType = "";

        String tpmc = "";

        String bdmz = "";
        String bdmzsj = "";
        String bdpz = "";
        String bdpzsj = "";


        if (jcmbj.equals("j")) {
            bt = "您预约的汽车已进站";
            sj = data.getJmrqsj();
            mbid = ycnIP.getMbidjm();
            webType = ycnIP.getMbidjmtype();
            tpmc = data.getJkwjchpjm();

        } else if (jcmbj.equals("c")) {
            bt = "您预约的汽车已出站";
            sj = data.getCmrqsj();
            mbid = ycnIP.getMbidcm();
            webType = ycnIP.getMbidcmtype();
            tpmc = data.getJkwjchpcm();
            bdmz = data.getMz();
            bdmzsj = data.getMzTime();
            bdpz = data.getPz();
            bdpzsj = data.getPzTime();

        }
        StationInfo stationInfo = stationDao.selectCzByZm(data.getBlztms());//根据TMS查询站信息
        String dbm = stationInfo.getStationDbm();
        WxPushDataMsg msg = new WxPushDataMsg();
        msg.setFirst(new WxPushData(bt, ""));

        msg.setKeyword1(new WxPushData(data.getCph(), "#173177"));//车牌号
        msg.setKeyword2(new WxPushData(data.getJccz(), "#173177"));//车站
        msg.setKeyword3(new WxPushData(data.getHqhw(), "#173177"));//预约货位
        msg.setKeyword4(new WxPushData(sj, "#173177"));//进出门时间


        msg.setRemark(new WxPushData("", ""));//末尾详情


        String date = DateUtil.getNowTime();//

        String url = jcmbj.equals("j") ? urlConfig.getMUrl() + "qr_code/push" + "?cph=" + data.getCph() +
                "&tms=" + data.getBlztms() +
                "&dbm=" + dbm +
                "&tpmc=" + tpmc +
                "&jcmsj=" + sj +
                "&sbbh=" + data.getSbbh() +
                "&zpmc=" + data.getJkwjchpcm() +
                "&jcbj=" + data.getJcmbj() +
                "&czm=" + data.getJccz() +
                "&type=" + webType +
                "&date=" + date
                :
                urlConfig.getMUrl() + "qr_code/push" + "?cph=" + data.getCph() +
                        "&tms=" + data.getBlztms() +
                        "&dbm=" + dbm +
                        "&tpmc=" + tpmc +
                        "&jcmsj=" + sj +
                        "&sbbh=" + data.getSbbh() +
                        "&zpmc=" + data.getJkwjchpcm() +
                        "&jcbj=" + data.getJcmbj() +
                        "&czm=" + data.getJccz() +
                        "&bdmz=" + bdmz + "&bdmzsj=" + bdmzsj + "&bdpz=" + bdpz + "&bdpzsj=" + bdpzsj +
                        "&type=" + webType +
                        "&date=" + date;
        logger.info(url);
        wxPushMsgToUser(data.getWxyyid(), msg, mbid, url);


    }


    /**
     * 将成组预约合并
     *
     * @param reData
     * @return
     */
    private List<PushYyToHzReData.ReturnData> fenpeiBz(PushYyToHzReData reData) {
        Map<String, List<PushYyToHzReData.ReturnData>> map = new HashMap<>();
        for (PushYyToHzReData.ReturnData data1 : reData.getReturnData()) {

            if (map.containsKey(data1.getBzid())) {
                //map有发货人组,添加
                map.get(data1.getBzid()).add(data1);
            } else {
                List<PushYyToHzReData.ReturnData> ll = new ArrayList<>();
                ll.add(data1);
                map.put(data1.getBzid(), ll);
            }
        }
        List<PushYyToHzReData.ReturnData> listRe = new ArrayList<>();
        for (String key :
                map.keySet()) {
            List<String> xqds = new ArrayList<>();
            StringBuffer xqdBf = new StringBuffer();
            List<PushYyToHzReData.ReturnData> list = map.get(key);
            PushYyToHzReData.ReturnData dataBZ = list.get(0);
            for (PushYyToHzReData.ReturnData dataBZ2 : list
            ) {
                if (!org.apache.commons.lang3.StringUtils.isEmpty(dataBZ2.getXqdh())) {
                    xqds.add(dataBZ2.getXqdh());

                    String xqdSX = dataBZ2.getXqdh().substring(dataBZ2.getXqdh().length() - 4, dataBZ2.getXqdh().length());
                    xqdBf.append("【...").append(xqdSX).append("】");
                }
            }
            dataBZ.setXqdhs(xqds);
            dataBZ.setXqdh(new String(xqdBf));
            listRe.add(dataBZ);

        }

        return listRe;
    }


    ////


    private List<PushYyToSjReData.ReturnData> fenpeiBzSj(PushYyToSjReData reData) {
        Map<String, List<PushYyToSjReData.ReturnData>> map = new HashMap<>();
        for (PushYyToSjReData.ReturnData data1 : reData.getReturnData()) {

            if (map.containsKey(data1.getBzid())) {
                //map有发货人组,添加
                map.get(data1.getBzid()).add(data1);
            } else {
                List<PushYyToSjReData.ReturnData> ll = new ArrayList<>();
                ll.add(data1);
                map.put(data1.getBzid(), ll);
            }
        }
        List<PushYyToSjReData.ReturnData> listRe = new ArrayList<>();
        for (String key :
                map.keySet()) {
            List<String> xqds = new ArrayList<>();
            StringBuffer xqdBf = new StringBuffer();
            List<PushYyToSjReData.ReturnData> list = map.get(key);
            PushYyToSjReData.ReturnData dataBZ = list.get(0);
            for (PushYyToSjReData.ReturnData dataBZ2 : list
            ) {
                if (!org.apache.commons.lang3.StringUtils.isEmpty(dataBZ2.getXqdh())) {
                    xqds.add(dataBZ2.getXqdh());

                    String xqdSX = dataBZ2.getXqdh().substring(dataBZ2.getXqdh().length() - 4, dataBZ2.getXqdh().length());
                    xqdBf.append("【...").append(xqdSX).append("】");
                }
            }
            dataBZ.setXqdhs(xqds);
            dataBZ.setXqdh(new String(xqdBf));
            listRe.add(dataBZ);

        }

        return listRe;
    }

}
