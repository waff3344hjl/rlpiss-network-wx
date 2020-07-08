package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 司机获取货主分享的需求单信息    请求bean
 * @Author HJL
 * @Date Created in 2020-04-26
 */
@Data
@XStreamAlias("parameter")
public class QueryShareData implements Serializable {
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


    @XStreamAlias("hyid") //
    private String hyid;


    public static QueryShareData getThis(String openid, String yhid, String wxip, StationInfo stationInfo) {
        QueryShareData data = new QueryShareData();
        if (!StringUtils.isEmpty(openid)) {
            data.setWxopenid(openid);
        }
        if (!StringUtils.isEmpty(yhid)) {
            data.setHyid(yhid);
        }
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());
        return data;
    }

    public static String getXML(String openid, String yhid, String wxip, StationInfo stationInfo) {
        QueryShareData data = getThis(openid, yhid, wxip, stationInfo);
        return XMLChange.objectToXml(data);
    }

}
