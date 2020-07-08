package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description  访问首页停限装  访问体Bean
 * @Author HJL
 * @Date Created in 2020-04-23
 */
@Data
@XStreamAlias("parameter")
public class ShouYeForTxzInfoData  implements Serializable {
    private String czm;

    @XStreamAlias("mlid") //
    private String mlid;//停限装命令ID

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

    public static ShouYeForTxzInfoData getThis(StationInfo info, String mlid){
        ShouYeForTxzInfoData data = new ShouYeForTxzInfoData();
        data.setCzdbm(info.getStationDbm() + "");
        data.setCztms(info.getStationTms() + "");
        data.setMlid(mlid);
        data.setUserid("wx");
        data.setUser("wx");
        data.setIp("wxip");
        return data;
    }

    public static String getXML(StationInfo info,String mlid){
        ShouYeForTxzInfoData data = getThis(info,mlid) ;
        return XMLChange.objectToXml(data);
    }
}
