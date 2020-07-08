package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 货主分享需求单给司机   请求bena
 * @Author HJL
 * @Date Created in 2020-04-26
 */
@Data
@XStreamAlias("parameter")
public class ShareData implements Serializable {
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


    @XStreamAlias("wxid") //货主id
    private String wxid;


    @XStreamAlias("ydydh00") //需求单号
    private String ydydh00;

    @XStreamAlias("fxwxid") //司机id
    private String fxwxid;


    @XStreamAlias("lx") //
    private String lx;//到货 0，发货1


    @XStreamAlias("blzmc") //办理站
    private String blzmc;


    public static ShareData setThis(String wxip, String hzid, String sjid, String xqdh, String lx, StationInfo stationInfo) {
        ShareData data = new ShareData();
        data.setWxid(hzid);
        data.setFxwxid(sjid);
        data.setYdydh00(xqdh);
        data.setLx(lx);
        data.setBlzmc("");

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());
        return data;

    }

    public static String getXml(String wxip, String hzid, String sjid, String xqdh,  String lx,StationInfo stationInfo) {
        ShareData data = setThis(wxip, hzid, sjid, xqdh, lx,stationInfo);
        return XMLChange.objectToXml(data);
    }

}
