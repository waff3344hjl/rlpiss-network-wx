package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 货主根据手机号查询注册司机信息
 * @Author HJL
 * @Date Created in 2020-04-24
 */
@Data
@XStreamAlias("parameter")
public class SJQueryData implements Serializable {
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


    @XStreamAlias("sjh") //
    private String sjh;

    public static SJQueryData getThis(User user, StationInfo stationInfo) {
        SJQueryData data = new SJQueryData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp("wx");
        data.setCztms(StringUtils.isEmpty(stationInfo.getStationTms()) ? "" : stationInfo.getStationTms());
        data.setCzdbm(StringUtils.isEmpty(stationInfo.getStationDbm()) ? "" : stationInfo.getStationDbm());

        data.setSjh(StringUtils.isEmpty(user.getSjh()) ? "" : user.getSjh());
        return data;
    }

    public static String getXML(User user, StationInfo stationInfo) {
        SJQueryData data = getThis(user, stationInfo);
        return XMLChange.objectToXml(data);
    }
}
