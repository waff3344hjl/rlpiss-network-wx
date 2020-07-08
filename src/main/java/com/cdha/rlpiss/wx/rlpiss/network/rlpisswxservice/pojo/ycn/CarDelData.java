package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 删除常用车辆。
 * @Author HJL
 * @Date Created in 2020-04-23
 */
@Data
@XStreamAlias("parameter")
public class CarDelData implements Serializable {
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


    @XStreamAlias("clxxid") //
    private String clxxid;

    public static CarDelData setThis(String id,String wxip, StationInfo stationInfo){
        CarDelData data =new CarDelData();
        data.setClxxid(id);

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(stationInfo.getStationDbm() + "");
        data.setCztms(stationInfo.getStationTms() + "");

        return data;
    }

    public static String getXML (String id,String wxip, StationInfo stationInfo){
        CarDelData data = setThis(id,wxip,stationInfo);
        return XMLChange.objectToXml(data);
    }

}
