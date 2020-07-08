package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserAndFrend;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 添加司机
 * @Author HJL
 * @Date Created in 2020-04-24
 */
@Data
@XStreamAlias("parameter")
public class SjAddData implements Serializable {
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


    @XStreamAlias("yhid") //
    private String yhid;


    @XStreamAlias("yhhyid") //
    private String yhhyid;

    public static SjAddData getThis(UserAndFrend uf, StationInfo stationInfo) {
        SjAddData data = new SjAddData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp("wx");
        data.setCztms(StringUtils.isEmpty(stationInfo.getStationTms()) ? "" : stationInfo.getStationTms());
        data.setCzdbm(StringUtils.isEmpty(stationInfo.getStationDbm()) ? "" : stationInfo.getStationDbm());
        data.setYhid(uf.getHz().getYhid());
        data.setYhhyid(uf.getSj().getYhid());
        return data;
    }

    public static String getXML(UserAndFrend uf, StationInfo stationInfo) {
        SjAddData data = getThis(uf, stationInfo);
        return XMLChange.objectToXml(data);
    }
}
