package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;


import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@Data
@XStreamAlias("parameter")
public class ZXImgInfoData implements Serializable {

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


    @XStreamAlias("pjid")
    private String pjid;

    @XStreamAlias("zxbj")
    private String zxbj;

    private static ZXImgInfoData getInstance(String pjid, String zxbj, String wxip, String tms,String dbm) {
        ZXImgInfoData data = new ZXImgInfoData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(tms);
        data.setCzdbm(dbm);

        data.setPjid(pjid);
        data.setZxbj(zxbj);
        return data;
    }

    public static String getXML(String pjid, String zxbj, String wxip, String tms,String dbm) {
        ZXImgInfoData data = getInstance(pjid, zxbj, wxip, tms,dbm);
        return XMLChange.objectToXml(data);
    }
}