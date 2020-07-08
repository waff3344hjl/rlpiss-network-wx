package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 集装箱清单查询---请求体
 * @Author HJL
 * @Date Created in 2020-05-20
 */
@Data
@XStreamAlias("parameter")
public class JzxQdQueryData implements Serializable {
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

    @XStreamAlias("yhsjh")//用户手机号----废弃
    private String yhsjh;

    @XStreamAlias("wxopenid")//用户手机号
    private String yhwxopenid;

    @XStreamAlias("hysjh") //会员手机---用户手机
    private String sjh;

    @XStreamAlias("sfbj") //
    private String sfbj;


    private static JzxQdQueryData getInstance(String yhsjh,String yhwxid,String sjh, String sfbj, String wxip, StationInfo stationInfo) {
        JzxQdQueryData data = new JzxQdQueryData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(stationInfo.getStationTms());
        data.setCzdbm(stationInfo.getStationDbm());


        data.setSfbj(StringUtils.changNull(sfbj));
        data.setSjh(StringUtils.changNull(sjh));

        data.setYhsjh(StringUtils.changNull(yhsjh));
        data.setYhwxopenid(StringUtils.changNull(yhwxid));

        return data;

    }

    public static String getXML(String yhsjh,String yhwxid,String sjh, String sfbj, String wxip, StationInfo stationInfo) {
        JzxQdQueryData data = getInstance( yhsjh, yhwxid,sjh, sfbj, wxip, stationInfo);
        return XMLChange.objectToXml(data);
    }


}
