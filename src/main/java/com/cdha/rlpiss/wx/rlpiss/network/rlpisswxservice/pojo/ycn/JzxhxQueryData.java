package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description 集装箱还箱
 * @Author HJL
 * @Date Created in 2020-06-06
 */


@Data
@XStreamAlias("parameter")
public class JzxhxQueryData {
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



    @XStreamAlias("wxopenid") //
    private String wxopenid;//微信ID


    public static JzxhxQueryData getInstance(String openid,String wxip, StationInfo stationInfo) {
        JzxhxQueryData data = new JzxhxQueryData();
        data.setWxopenid(openid);
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());
        return data;
    }

    public static String getXML(String openid,String wxip,StationInfo stationInfo) {
        JzxhxQueryData data = getInstance(openid,wxip,stationInfo);
        return XMLChange.objectToXml(data);
    }
}
