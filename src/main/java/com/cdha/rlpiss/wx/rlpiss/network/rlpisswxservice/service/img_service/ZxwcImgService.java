package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.img_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.ZXImgInfoAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.ZXImgInfoData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 装卸完成图片查询
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Service
public class ZxwcImgService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;


    @Autowired
    IYCNRlUserDao dao;

    @Autowired
    IYCNRLStationDao stationDao;

    public BaseResponseData<List<String>> getImageZxwc(String tms,
                                                       String dbm,
                                                       String pjid,
                                                       String type) throws IOException {
        String rqXML = ZXImgInfoData.getXML(pjid,type,ycnIP.getWxip(),tms,dbm);
        String reXML = manager.getResponseByAQPT("",ycnIP.getGetZxInfoByPJID(),rqXML,ycnIP.getUrl());
        ZXImgInfoAqptReData reData = XMLChange.xmlToObject(reXML,ZXImgInfoAqptReData.class);
        if (!"0".equals(reData.getHead().getFHZBS())) {
            return new BaseResponseData<>("-1","",null);
        }

        List<String> reImgs = new ArrayList<>();
        for (ZXImgInfoAqptReData.ReturnData imgs: reData.getReturnData()
             ) {
            for (ZXImgInfoAqptReData.Item item:imgs.getItem()
                 ) {
                reImgs.add(item.getImg())   ;
            }

        }
        return new BaseResponseData<>("0","",reImgs);
    }
}
