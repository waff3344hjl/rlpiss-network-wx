package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 根据微信ID获取用户信息
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@Data
@XStreamAlias("parameter")
public class LoginData implements Serializable {
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
    private String wxopenid;

    public static LoginData setLoginDataByOpenid(String openid,String wxip, StationInfo stationInfo) {
        LoginData data = new LoginData();
        data.setWxopenid(openid);
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());
        return data;
    }

    public static String getXML(String openid,String wxip,StationInfo stationInfo) {
        LoginData data = setLoginDataByOpenid(openid,wxip,stationInfo);
        return XMLChange.objectToXml(data);
    }
}
