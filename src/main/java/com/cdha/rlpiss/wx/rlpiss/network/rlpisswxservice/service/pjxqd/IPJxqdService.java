package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.pjxqd;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.PjXqdAqptReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.PjXqdData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 评价需求单
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Service
public class IPJxqdService {
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
     * 评价需求单
     *
     * @param fzmc    1
     * @param dzmc    1
     * @param djbh    1
     * @param blzdbm  1
     * @param blzmc   1
     * @param blztms  1
     * @param byjssj  1
     * @param bykssj  1
     * @param hqhwmc  1
     * @param pmmc    1
     * @param startpj 1
     * @param yx      1
     * @param edit    1
     * @return 1
     */
    public BaseResponseData<List<String>> setPj(String fzmc, String dzmc, String djbh, String blzdbm, String blzmc, String blztms, String byjssj, String bykssj, String hqhwmc, String pmmc, String startpj, String yx, String edit) {
        String rqXML = PjXqdData.getXML(fzmc, dzmc, djbh, blzdbm, blzmc, blztms, byjssj, bykssj, hqhwmc, pmmc, startpj, yx, edit, ycnIP.getWxip());
        String reXML = manager.getResponseByAQPT("", ycnIP.getPjxqd(), rqXML, ycnIP.getUrl());
        PjXqdAqptReData reData = XMLChange.xmlToObject(reXML, PjXqdAqptReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            throw new GmManagerException("安全平台返回：评价失败");
        }
        return new BaseResponseData<>("0","",null);
    }
}
