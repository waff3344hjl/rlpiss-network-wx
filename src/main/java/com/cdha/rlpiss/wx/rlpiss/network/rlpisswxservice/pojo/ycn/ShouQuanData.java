package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 用户授权 ---- 安全平台 请求体
 * @Author HJL
 * @Date Created in 2020-05-21
 */
@Data
@XStreamAlias("parameter")
public class ShouQuanData implements Serializable {
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


    @XStreamAlias("wxopenid") //货主 物流公司微信id
    private String hzwxid;

    @XStreamAlias("hzsj") //货主 物流公司手机号
    private String hzsj;


    @XStreamAlias("hysjh") //被授权人手机号
    private String sqrsj;

    private static ShouQuanData getInstance(String hzwxid, String hzsj, String sqrsj, String wxip, StationInfo stationInfo){
        ShouQuanData data = new ShouQuanData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());

        data.setHzwxid(StringUtils.changNull(hzwxid));
        data.setHzsj(StringUtils.changNull(hzsj));
        data.setSqrsj(StringUtils.changNull(sqrsj));

        return  data;
    }

    public static  String getXML (String hzwxid,String hzsj,String sqrsj,  String wxip,StationInfo stationInfo){
        ShouQuanData data = getInstance( hzwxid, hzsj, sqrsj,   wxip, stationInfo);
        return XMLChange.objectToXml(data);
    }


}
