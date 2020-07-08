package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@Data
@XStreamAlias("parameter")
public class ShouYeData implements Serializable {
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

    public static ShouYeData getSyData(StationInfo info) {
        ShouYeData data = new ShouYeData();
        data.setCzdbm(info.getStationDbm() + "");
        data.setCztms(info.getStationTms() + "");
        data.setUserid("wx");
        data.setUser("wx");
        data.setIp("wxip");
        return data;
    }

    public static String getXml(StationInfo info) {
        ShouYeData data = getSyData(info);
        return XMLChange.objectToXml(data);
    }
}
