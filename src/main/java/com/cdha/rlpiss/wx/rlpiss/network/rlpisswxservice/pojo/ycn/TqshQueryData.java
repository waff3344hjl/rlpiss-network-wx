package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description 提前上货
 * @Author HJL
 * @Date Created in 2020-06-06
 */


@Data
@XStreamAlias("parameter")
public class TqshQueryData {
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

    @XStreamAlias("TYR") //
    private String tyr;



    @XStreamAlias("wxopenid") //
    private String wxopenid;//微信ID


    public static TqshQueryData getInstance(String openid,String tyr, String wxip, StationInfo stationInfo) {
        TqshQueryData data = new TqshQueryData();
        data.setWxopenid(openid);
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setTyr(StringUtils.changNull(tyr));
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());
        return data;
    }

    public static String getXML(String openid,String tyr, String wxip,StationInfo stationInfo) {
        TqshQueryData data = getInstance(openid, tyr,wxip,stationInfo);
        return XMLChange.objectToXml(data);
    }
}
