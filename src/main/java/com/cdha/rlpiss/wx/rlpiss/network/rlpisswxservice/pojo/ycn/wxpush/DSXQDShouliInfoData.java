package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.wxpush;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-28
 */
@Data
@XStreamAlias("parameter")
public class DSXQDShouliInfoData implements Serializable {
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

    @XStreamAlias("ydhs")
    private String ydhs;


    public static DSXQDShouliInfoData getInstance(String wxip,  String czdbm,String  tms,String sjh,String ydhs) {
        DSXQDShouliInfoData data = new DSXQDShouliInfoData();

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(tms);
        data.setCzdbm(czdbm);
        data.setSjh(sjh);
        data.setYdhs(ydhs);
        return data;
    }

    public static String getXML(String wxip, String czdbm,String  tms,String sjh,String ydhs) {
        DSXQDShouliInfoData data = getInstance(wxip, czdbm,tms,sjh,ydhs);
        return XMLChange.objectToXml(data);
    }
}
