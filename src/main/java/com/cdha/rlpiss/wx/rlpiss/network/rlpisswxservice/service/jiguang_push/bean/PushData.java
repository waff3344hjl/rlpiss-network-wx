package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean;



import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Data
@XStreamAlias("parameter")
public class PushData implements Serializable {
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

    public static PushData getInstance(String wxip, String cztms, String czdbm) {
        PushData data = new PushData();

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(cztms);
        data.setCzdbm(czdbm);
        return data;
    }

    public static String getXML(String wxip, String cztms, String czdbm) {
        PushData data = getInstance(wxip, cztms, czdbm);
        return XMLChange.objectToXml(data);
    }
}
