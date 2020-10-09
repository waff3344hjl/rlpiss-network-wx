package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.dsly_service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.UrlConfig;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IzhwlUrlDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.wxpush.WxPushData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.wxpush.WxPushDataMsg;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl.*;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.JiGuangPushServiceImpl;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushBean;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.AesEncrypt;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.MyBase64Utils;
import com.cdha.wechatsub.wxtools.bean.TemplateSender;
import com.cdha.wechatsub.wxtools.bean.result.TemplateSenderResult;
import com.cdha.wechatsub.wxtools.dao.serviceImpl.WxService;
import com.cdha.wechatsub.wxtools.exception.WxErrorException;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.*;

/**
 * @Description 推送
 * @Author HJL
 * @Date Created in 2020/7/17
 */
@Slf4j
@Service
public class ZxWlPushService {
    private final
    RestTemplateManager manager;
    private final
    UrlConfig urlConfig;
    private final
    IzhwlUrlDao dao;
    private final
    JiGuangPushServiceImpl jiGuangPushService;
    private final
    WxService wxService;

    @Autowired
    public ZxWlPushService(RestTemplateManager manager, UrlConfig urlConfig, IzhwlUrlDao dao, JiGuangPushServiceImpl jiGuangPushService, WxService wxService) {
        this.manager = manager;
        this.urlConfig = urlConfig;
        this.dao = dao;
        this.jiGuangPushService = jiGuangPushService;
        this.wxService = wxService;
    }

    private static <T> String getJson(BaseRequestData<T> wlrq) {
        StringBuffer jsonBf = new StringBuffer();
        String json = JSON.toJSONString(wlrq);
        try {
            jsonBf.append(AesEncrypt.encrypt(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(jsonBf);
    }

    /**
     * @param pushType   推送类型    到货推送 or 出线推送 or 运到推送
     * @param jsqUrlPath 物流服务接受器 url空间地址
     * @param meUrlPath  物流服务具体服务 url空间地址
     * @param dslyType   多式联运应用类型
     * @param dslyToken  多式联运token
     * @param wxMbid     微信推送模板ID
     * @param wxUrl      微信推送详情URL
     * @param logString  日志类型
     */
    private void pushMSG(String pushType, String jsqUrlPath, String meUrlPath, String dslyType, String dslyToken, String wxMbid, String wxUrl, String logString) {
        List<ZxwlServicePath> pathList = getAllServicePath();
        log.info("=======" + logString + "  服务器地址  ====== ：" + Objects.requireNonNull(pathList).toString() + "\n" + pathList.size());
        for (ZxwlServicePath path : pathList
        ) {
            if (null != path.getTmsList()) {
                for (ZxwlServicePath.CzTms tms : path.getTmsList()
                ) {
                    BaseRequestData<List<ZxwlServicePath.CzTms>> rlRqData = setWrRqData(tms);
                    StringBuffer bf = setUrlBf(path, jsqUrlPath, meUrlPath);
                    BaseDSLYRqData<BaseDSLYRqData.BusinessData> rqData = setDslyRqData(bf, path, rlRqData, dslyType, dslyToken, logString);
                    try {
                        String reString = manager.getRlpissDataString(urlConfig.getDslyUrl(), JSON.toJSONString(rqData));
                        log.info("=======" + logString + "  返回Json  ====== ：" + reString);
                        JSONObject object = JSONObject.parseObject(reString);
                        String returnCode = object.getString("returnCode");
                        if ("0".equals(returnCode)) {
                            String data = object.getString("data");
                            log.info("=======" + logString + "  data ====== ：" + data);
                            if (!StringUtils.isEmpty(object.getString("code")) && !"0".equals(object.getString("code"))) {
                                //不解密，直接解析。。。当服务端发生异常
                                log.info("=======" + logString + "  不解密 ====== ：" + data);
                                return;
                            } else {
                                String jiexi = AesEncrypt.desEncrypt(data);
                                log.info("=======" + logString + "  解析完成  ====== ：" + "\n" + jiexi);
                                JSONObject objectWl = JSONObject.parseObject(jiexi);
                                log.info("=======" + logString + "  解析完成   JSONObject ====== ：" + objectWl);
                                String code = objectWl.getString("code");
                                if ("0".equals(code)) {
                                    //解析成功 且 code =0,且解析list
                                    String listTxt = JSONArray.toJSONString(objectWl.get("rsData"));
                                    if ("4".equals(pushType)) {
                                        //好友推送---只推送APP
                                        List<ZhwlMSInfoForPushPojo> modelList = JSONArray.parseArray(listTxt, ZhwlMSInfoForPushPojo.class);
                                        log.info("=======" + logString + "  解析并转化为  List  ====== ：" + modelList.toString());
                                        if (modelList.size() > 0) {
                                            for (ZhwlMSInfoForPushPojo msInfoForPushPojo : modelList
                                            ) {
                                                boolean jgFlag = jgPushMsgToUser(pushType, msInfoForPushPojo.getYhid(), null, msInfoForPushPojo, null, logString); //添加好友 =---进行极光推送
                                            }
                                        }
                                    } else if ("5".equals(pushType)) {
                                        //，新运输任务通知---只推送APP
                                        List<NewTask> newTaskList = JSONArray.parseArray(listTxt, NewTask.class);
                                        log.info("=======" + logString + "  解析并转化为  List  ====== ：" + newTaskList.toString());
                                        if (newTaskList.size() > 0) {
                                            for (NewTask task : newTaskList
                                            ) {
                                                boolean jgFlag = jgPushMsgToUser(pushType, task.getSjid(), null, null, task, logString); //新任务下达 =---进行极光推送
                                            }
                                        }
                                    } else {
                                        //其他推送---app+微信公众号
                                        List<ZhwlTzModel> modelList = JSONArray.parseArray(listTxt, ZhwlTzModel.class);
                                        log.info("=======" + logString + "  解析并转化为  List  ====== ：" + modelList.toString());
                                        if (modelList.size() > 0) {
                                            for (ZhwlTzModel zhwlTzModel : modelList
                                            ) {
                                                String yhid = zhwlTzModel.getYhid();
                                                String wxid = zhwlTzModel.getWxopenid();
                                                List<ZhwlXqdInfo> xqdInfoList = zhwlTzModel.getList();
                                                if (xqdInfoList != null && xqdInfoList.size() > 0) {
                                                    boolean jgFlag = jgPushMsgToUser(pushType, yhid, xqdInfoList, null, null, logString); //进行极光推送
                                                    if (!StringUtils.isEmpty(wxid)) {
                                                        //如果微信ID不为空，则公众号继续推送
                                                        boolean wxFlag = wxPushMsgToUser(pushType, wxid, zhwlTzModel, xqdInfoList, wxMbid, wxUrl, logString); //进行微信模板推送
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("=======" + logString + "   获取数据异常  ====== ：" + e.getMessage());
                        continue;
                    }
                }
            }

        }
    }

    /**
     * 微信模板推送
     *
     * @param pushType    推送类型    到货推送 or 出线推送 or 运到推送
     * @param wxid        微信openid
     * @param zhwlTzModel 需求单list
     * @param logString   日志标记
     * @param wxMBiD      模板id
     * @param wxMbUrl     详情网页url
     * @return 是否推送成功
     */
    private boolean wxPushMsgToUser(String pushType, String wxid, ZhwlTzModel zhwlTzModel, List<ZhwlXqdInfo> xqdInfoList, String wxMBiD, String wxMbUrl, String logString) throws WxErrorException {
        WxPushDataMsg msgInfo = getWxPushDataByType(pushType, zhwlTzModel, xqdInfoList);
//        wxid = "orEK7uBS3Bpo86RQlAezfGd_6Wkk";
        String jsonStr = JSON.toJSONString(xqdInfoList).trim();
        if (!StringUtils.isEmpty(wxMbUrl)) {
            String urlObj = MyBase64Utils.encode(jsonStr).replace("==", "**"); // List转json 并进行base64编码。同时对base64字符串中=号进行转码
            StringBuffer urlBf = new StringBuffer();
            urlBf.append(wxMbUrl);
            urlBf.append(urlObj);
            String url = new String(urlBf);
            log.info("\n" + url);
            wxPush(wxid, msgInfo, wxMBiD, url, wxService);
        } else {
            wxPush(wxid, msgInfo, wxMBiD, "", wxService);
        }

        return true;
    }

    private WxPushDataMsg getWxPushDataByType(String pushType, ZhwlTzModel zhwlTzModel, List<ZhwlXqdInfo> xqdInfoList) {
        WxPushDataMsg msgInfo = new WxPushDataMsg();
        switch (pushType) {
            case "1":
                msgInfo.setFirst(new WxPushData("您的货物已到站", ""));//标题。。
                msgInfo.setKeyword1(new WxPushData(xqdInfoList.get(0).getHph(), "#173177"));
                msgInfo.setKeyword2(new WxPushData(xqdInfoList.get(0).getBlzmc(), "#173177"));
                msgInfo.setRemark(new WxPushData("请合理安排取货时间", "#173177"));//末尾详情
                break;
            case "2":
                msgInfo.setFirst(new WxPushData("您的货物已装车出线", ""));//标题。。
                msgInfo.setKeyword1(new WxPushData(xqdInfoList.get(0).getXqdh(), "#173177"));
                msgInfo.setKeyword2(new WxPushData(xqdInfoList.get(0).getBlzmc(), "#173177"));
                msgInfo.setRemark(new WxPushData("谢谢您的使用", "#173177"));//末尾详情
                break;
            case "3":
                StringBuffer xqdsBf = new StringBuffer();
                StringBuffer pmsBf = new StringBuffer();
                for (int i = 0; i < xqdInfoList.size(); i++) {
                    xqdsBf.append("[").append(StringUtils.changNull(xqdInfoList.get(i).getXqdh())).append("]");
                    pmsBf.append("[").append(StringUtils.changNull(xqdInfoList.get(i).getPm())).append("]");
                    if (i < xqdInfoList.size() - 1) {
                        xqdsBf.append(",");
                        pmsBf.append(",");
                    }
                }

                StringBuffer qsbjBf = setQslx(zhwlTzModel);
                StringBuffer sjxm = new StringBuffer();
                sjxm.append(StringUtils.isEmpty(zhwlTzModel.getSjxm()) ? "未知" : zhwlTzModel.getSjxm());
                StringBuffer qswcsj = new StringBuffer();
                qswcsj.append(StringUtils.isEmpty(zhwlTzModel.getQswcsj()) ? "未知" : zhwlTzModel.getQswcsj());

                msgInfo.setFirst(new WxPushData("您的货物已运到目的地", ""));//标题。。
                msgInfo.setKeyword1(new WxPushData(new String(xqdsBf), "#173177"));//需求单
                msgInfo.setKeyword2(new WxPushData(new String(pmsBf), "#173177"));//品名
                msgInfo.setKeyword3(new WxPushData(new String(qsbjBf), "#173177"));//运输类型
                msgInfo.setKeyword4(new WxPushData(new String(sjxm), "#173177"));//司机姓名
                msgInfo.setKeyword5(new WxPushData(new String(qswcsj), "#173177"));//运输完成时间
                msgInfo.setRemark(new WxPushData("谢谢您的使用", "#173177"));//末尾详情
                break;
            default:
        }
        return msgInfo;
    }

    private StringBuffer setQslx(ZhwlTzModel zhwlTzModel) {
        StringBuffer qsbjBf = new StringBuffer();
        if ("1".equals(zhwlTzModel.getYslx())) {
            //整车
            if (zhwlTzModel.getQshbj() != null) {
                switch (zhwlTzModel.getQshbj()) {
//                    0：收货 1：发货 2：提前上货
                    case "0":
                        qsbjBf.append("整车收货");
                        break;
                    case "1":
                        qsbjBf.append("整车发货");
                        break;
                    case "2":
                        qsbjBf.append("整车提前上货");
                        break;
                    default:
                }
            }


        } else if ("3".equals(zhwlTzModel.getYslx())) {
            //集装箱
            switch (zhwlTzModel.getQshbj()) {
//                    0：提箱 1：送货 2：提送箱
                case "0":
                    qsbjBf.append("集装箱提箱");
                    break;
                case "1":
                    qsbjBf.append("集装箱送箱");
                    break;
                case "2":
                    qsbjBf.append("集装箱提送箱");
                    break;
                default:
            }
        }
        return qsbjBf;
    }


    public static void wxPush(String wxopenid, WxPushDataMsg msgInfo, String wxMBiD, String wxMbUrl, WxService wxService) throws WxErrorException {
        TemplateSender sender = new TemplateSender();
        sender.setTemplate_id(wxMBiD);
        sender.setTouser(wxopenid);
        sender.setData(msgInfo);
        if (!StringUtils.isEmpty(wxMbUrl)) {
            sender.setUrl(wxMbUrl);//设置模版信息的超链接
        }
        TemplateSenderResult result = wxService.templateSend(sender);
    }

    /**
     * 极光推送
     *
     * @param pushType    推送类型    到货推送 or 出线推送 or 运到推送
     * @param yhid        用户ID
     * @param xqdInfoList 需求单列表
     * @param logString   日志标记
     * @return 是否推送成功
     */
    private boolean jgPushMsgToUser(String pushType, String yhid, List<ZhwlXqdInfo> xqdInfoList, ZhwlMSInfoForPushPojo msInfoForPushPojo, NewTask task, String logString) {

        PushBean pushBean = getJgPushBean(pushType, xqdInfoList, msInfoForPushPojo, task);
        if (pushBean != null) {
            boolean flag = jiGuangPushService.pushAndroidByalias(pushBean, yhid);
            log.info("=======" + logString + "  推送完成  ====== ：" + flag);
            return flag;
        } else {
            return false;
        }
    }

    private PushBean getJgPushBean(String pushType, List<ZhwlXqdInfo> xqdInfoList, ZhwlMSInfoForPushPojo msInfoForPushPojo, NewTask task) {
        StringBuffer alertBf = new StringBuffer();
        switch (pushType) {
            case "1":
                alertBf.append("您有新的货物(品名：").
                        append(StringUtils.changNull(xqdInfoList.get(0).getPm())).
                        append(")").
                        append("已到达【").
                        append(StringUtils.changNull(xqdInfoList.get(0).getDzm())).
                        append("货场】");
                PushBean pushBean = new PushBean();
                pushBean.setTitle("货物已到达货场通知");
                pushBean.setAlert(new String(alertBf));
                return pushBean;

            case "2":
                alertBf.append("您的货物(品名：").
                        append(StringUtils.changNull(xqdInfoList.get(0).getPm())).
                        append(")").
                        append("已从【").
                        append(StringUtils.changNull(xqdInfoList.get(0).getDzm())).
                        append("货场】出发");
                PushBean pushBean2 = new PushBean();
                pushBean2.setTitle("");
                pushBean2.setAlert(new String(alertBf));
                return pushBean2;

            case "3":
                alertBf.append("您的货物(品名：").
                        append(StringUtils.changNull(xqdInfoList.get(0).getPm())).
                        append(")").
                        append("已运到目的地");
                PushBean pushBean3 = new PushBean();
                pushBean3.setTitle("货物运到通知");
                pushBean3.setAlert(new String(alertBf));
                return pushBean3;
            case "4":
                alertBf.append("用户【").
                        append(msInfoForPushPojo.getQqryhxm()).
                        append("】").
                        append("已添加你为好友，请这注意查看");
                PushBean pushBean4 = new PushBean();
                pushBean4.setTitle("好友添加通知");
                pushBean4.setAlert(new String(alertBf));
                return pushBean4;

            case "5":
                alertBf.append("用户【").
                        append(task.getYhxm()).
                        append("】").
                        append("聘请您运输一批货物，请这注意查看");
                Map<String, String> extras = new HashMap<>();
                extras.put("YSLX", StringUtils.changNull(task.getYslx()));
                extras.put("QSBJ", StringUtils.changNull(task.getQshbj()));
                PushBean pushBean5 = new PushBean();
                pushBean5.setTitle("您有新的运输任务");
                pushBean5.setAlert(new String(alertBf));
                pushBean5.setExtras(extras);
                return pushBean5;
            default:
                return null;

        }
    }

    /**
     * 构造多式联运请求参数
     *
     * @param bf        URL Bf
     * @param path      服务地址
     * @param rlRqData  物流参数
     * @param dslyType  多式联运type
     * @param dslyToken 多式联运token
     * @param logString 日志标记
     * @return BaseDSLYRqData<BaseDSLYRqData.BusinessData>
     */
    private BaseDSLYRqData<BaseDSLYRqData.BusinessData> setDslyRqData(StringBuffer bf, ZxwlServicePath path, BaseRequestData<List<ZxwlServicePath.CzTms>> rlRqData, String dslyType, String dslyToken, String logString) {
        BaseDSLYRqData.BusinessData businessData = new BaseDSLYRqData.BusinessData();//构造多式联运-rqdata参数
        businessData.setLj(new String(bf));
        businessData.setUrl(path.getHyzUrl() + "");
        String dataString = getJson(rlRqData);
        businessData.setData(dataString);


        BaseDSLYRqData<BaseDSLYRqData.BusinessData> rqData = new BaseDSLYRqData<>();//构造多式联运参数
        rqData.setGrant_Type(dslyType);//多式联运要求传递的参数---应用类型
        rqData.setAuthorization(dslyToken);//多式联运要求传递的参数---token
        rqData.setData(businessData);
        log.info("=======" + logString + "  物流参数  ====== ：" + rlRqData.toString());
        log.info("=======" + logString + "  请求参数  ====== ：" + rqData.toString());
        return rqData;
    }

    /**
     * 构造物流服务请求Url地址
     *
     * @param path      数据库获取素有服务地址
     * @param meUrlPath 当前推送的path
     * @return StringBuffer
     */
    private StringBuffer setUrlBf(ZxwlServicePath path, String jsqUrlPath, String meUrlPath) {
        StringBuffer bf = new StringBuffer(); //物流服务地址构造
        String jsqUrl = path.getJsqUrl() + jsqUrlPath;
        String meUrl = path.getWlUrl() + meUrlPath;
        bf.append(jsqUrl).append("#").append(meUrl);
        return bf;
    }

    /**
     * 构造物流服务---推送模块参数
     *
     * @param tms 车站TMS码
     * @return 参数
     */
    private BaseRequestData<List<ZxwlServicePath.CzTms>> setWrRqData(ZxwlServicePath.CzTms tms) {
        BaseRequestData<List<ZxwlServicePath.CzTms>> rlRqData = new BaseRequestData<>();//物流服务具体参数
        rlRqData.setMethod("app");
        List<ZxwlServicePath.CzTms> tmsList = new ArrayList<>();
        tmsList.add(tms);
        rlRqData.setRqData(tmsList);
        return rlRqData;
    }


    /**
     * 推送新好友添加信息
     */
    public void pushWhoAddMe() {
        String csMbId = "5EixVjLAx6LEro9HhcAP7xsIuNBAZ7MQxJXxygYlgNc";

        pushMSG("4", urlConfig.getPushJsqUrl(), urlConfig.getPushGetPushInfo(), "123", "123", csMbId, "", "好友添加通知");

    }

    /**
     * 到货通知
     */
    public void pushArrivalNotice() {
        String csMbId = "5EixVjLAx6LEro9HhcAP7xsIuNBAZ7MQxJXxygYlgNc";
        String baseMbUrl = urlConfig.getMUrl() + "qr_code/push?type=dh&objData=";
        pushMSG("1", urlConfig.getPushJsqUrl(), urlConfig.getPushDhUrl(), "123", "123", csMbId, baseMbUrl, "到货通知");
    }

    /**
     * 货物已发通知
     */
    public void pushDeliveryNotice() {
        String csMbid = "IVcgsh4gi3xb-Zar2eGHRXpiCvf0cf1geN9LKCJklSc";
        String baseMbUrl = urlConfig.getMUrl() + "qr_code/push?type=fh&objData=";
        pushMSG("2", urlConfig.getPushJsqUrl(), urlConfig.getPushFhUrl(), "123", "123", csMbid, baseMbUrl, "货物出线通知");
    }

    /**
     * 汽车运输完成通知
     */
    public void pushCompletionNotice() {
        String csMbid = "6xuJwuzdxO63-09u5xPTdKc9hh7RNjdG_KJ10ElHAp0";
        pushMSG("3", urlConfig.getPushJsqUrl(), urlConfig.getPushYswcUrl(), "123", "123", csMbid, "", "货物已运到目的地通知");
    }

    /**
     * 司机新任务通知
     */
    public void pushNewTask() {
        String csMbid = "6xuJwuzdxO63-09u5xPTdKc9hh7RNjdG_KJ10ElHAp0";
        pushMSG("5", urlConfig.getPushJsqUrl(), urlConfig.getPushNewTastUrl(), "123", "123", csMbid, "", "新的运输任务通知");
    }

    /**
     * 查询所有部署我服务的车站URL地址
     *
     * @return 车站信息
     */
    private List<ZxwlServicePath> getAllServicePath() {
        List<ZxwlURL> list = dao.getAllUrls();
        if (list != null && list.size() > 0) {
            List<ZxwlServicePath> pathList = new ArrayList<>();
            Map<String, List<ZxwlURL>> map = fenZu(list);
            for (String key :
                    map.keySet()) {
                List<ZxwlURL> list2 = map.get(key);
                ZxwlServicePath path = new ZxwlServicePath();
                List<ZxwlServicePath.CzTms> tmsList = new ArrayList<>();
                for (ZxwlURL model : list2
                ) {
                    String tms = model.getTms();
                    ZxwlServicePath.CzTms tmsModel = new ZxwlServicePath.CzTms();
                    tmsModel.setTmis(tms);
                    tmsList.add(tmsModel);
                }
                path.setTmsList(tmsList);
                path.setFubzNo(key);
                path.setHyzUrl(list2.get(0).getHyzUrl());
                path.setJsqUrl(list2.get(0).getJsqUrl());
                path.setWlUrl(list2.get(0).getWlUrl());
                pathList.add(path);
            }
            return pathList;
        } else {
            return null;
        }
    }

    //把地址 按 编组从新分组
    private static Map<String, List<ZxwlURL>> fenZu(List<ZxwlURL> list) {//map是用来接收分好的组的
        if (null == list) {
            return null;
        }

        Map<String, List<ZxwlURL>> map = new HashMap<>();
        String key;
        List<ZxwlURL> listTmp;
        for (ZxwlURL val : list) {
            key = val.getFubzNo();//按这个属性分组，map的Key
            listTmp = map.get(key);
            if (null == listTmp) {
                listTmp = new ArrayList<ZxwlURL>();
                map.put(key, listTmp);
            }
            listTmp.add(val);
        }
        return map;
    }
}
