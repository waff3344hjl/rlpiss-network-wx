package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.img_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JinChuMenImgAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.JinChuMenImgData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 进出门图片查询
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Service
public class JcmImgService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;


    @Autowired
    IYCNRlUserDao dao;

    @Autowired
    IYCNRLStationDao stationDao;

    public BaseResponseData<List<String>> getImageJCM(String tms, String jcmsj, String sbbh, String zpmc, String dbm) {
        logger.info("jcmRqData:"+tms+"\n"+jcmsj+"\n"+sbbh+"\n"+zpmc+"\n"+dbm);
        String rqXML = JinChuMenImgData.getXML(jcmsj, sbbh, zpmc, ycnIP.getWxip(), tms, dbm);
        logger.info("=== 进出门图片获取模块 ==="+rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getGetQcjcmImageInfo(), rqXML, ycnIP.getUrl());
        JinChuMenImgAqptReData reData = XMLChange.xmlToObject(reXML, JinChuMenImgAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9",XMLChange.xmlToObject(reXML, RegisterAQPTReData.class).getReturnData(),null);
        }
        List<String> imgs= new ArrayList<>();
        for (JinChuMenImgAqptReData.ReturnData img:reData.getReturnData()
             ) {
            imgs.add(img.getImg()) ;
        }
        return new BaseResponseData<>("0","",imgs);
    }
}
