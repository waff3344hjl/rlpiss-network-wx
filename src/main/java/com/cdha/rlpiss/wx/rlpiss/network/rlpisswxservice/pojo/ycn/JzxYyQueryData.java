package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 集装箱预约时，查询收货人、托运人、发站、到站 请求体
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Data
@XStreamAlias("parameter")
public class JzxYyQueryData implements Serializable {
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

    public static JzxYyQueryData getInstance(String dbm, String tms, String wxip) {
        JzxYyQueryData data = new JzxYyQueryData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(dbm + "");
        data.setCztms(tms + "");
        return data;
    }


    public static String getXML(String dbm, String tms, String wxip) {
        JzxYyQueryData data = getInstance(dbm, tms, wxip);
        return XMLChange.objectToXml(data);
    }

}
