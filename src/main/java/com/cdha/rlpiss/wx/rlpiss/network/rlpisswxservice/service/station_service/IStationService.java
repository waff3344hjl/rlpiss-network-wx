package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.station_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-20
 */
@Service
public class IStationService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IYCNRLStationDao dao;

    public BaseResponseData<List<StationInfo>> selectStations() {
        try {
            List<StationInfo> datas = dao.selectStations();
            if (datas != null && datas.size() > 0) {
                return new BaseResponseData<>("0", "", datas);
            } else {
                return new BaseResponseData<>("-1", "数据库没有数据", null);
            }
        } catch (Exception e) {
            logger.error("selectStations||--" + e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }
}
