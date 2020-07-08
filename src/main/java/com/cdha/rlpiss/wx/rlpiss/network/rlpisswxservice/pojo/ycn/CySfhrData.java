package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Data
@XStreamAlias("parameter")
public class CySfhrData implements Serializable {
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



    @XStreamAlias("cysfhr") //
    private String cysfhr;


    @XStreamAlias("cysfhrid") //
    private String cysfhrid;


    @XStreamAlias("wxopenid") //
    private String wxopenid;
    public static CySfhrData getThis(String openid, String cysfhr, String cysfhrid, String wxip, StationInfo stationInfo) {
        CySfhrData data = new CySfhrData();


        data.setUserid(openid);
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());

        data.setWxopenid(openid);
        data.setCysfhr(StringUtils.changNull(cysfhr));
        data.setCysfhrid(StringUtils.changNull(cysfhrid));

        return data;
    }

    public static String getXML(String openid, String cysfhr,String cysfhrid, String wxip, StationInfo stationInfo) {
        CySfhrData data = getThis(openid,cysfhr,cysfhrid, wxip, stationInfo);
        return XMLChange.objectToXml(data);
    }
}
