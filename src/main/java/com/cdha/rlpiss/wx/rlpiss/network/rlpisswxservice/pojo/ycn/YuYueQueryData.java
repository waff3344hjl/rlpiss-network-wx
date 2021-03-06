package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 预约查询 请求bean
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Data
@XStreamAlias("parameter")
public class YuYueQueryData implements Serializable {
    @XStreamAlias("userid") //
    private String userid;


    @XStreamAlias("user") //
    private String user;


    @XStreamAlias("ip") //
    private String ip;


    @XStreamAlias("cztms") //
    private String cztms;


    @XStreamAlias("czdbm") //
    private String czdbm;

    @XStreamAlias("cph") //
    private String cph;

    @XStreamAlias("yyrq") //
    private String yyrq;

    @XStreamAlias("wxopenid") //
    private String wxopenid;

    public static YuYueQueryData getThis(String openid, String wxip, StationInfo stationInfo) {
        YuYueQueryData data = new YuYueQueryData();
        data.setWxopenid(openid);
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCph("");
        data.setYyrq("");
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());
        return data;
    }

    public static String getXML(String openid, String wxip, StationInfo stationInfo) {
        YuYueQueryData data = getThis(openid, wxip, stationInfo);
        return XMLChange.objectToXml(data);
    }
}
