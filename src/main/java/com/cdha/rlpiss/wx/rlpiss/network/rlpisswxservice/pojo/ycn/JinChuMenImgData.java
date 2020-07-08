package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-05-09
 */
@Data
@XStreamAlias("parameter")
public class JinChuMenImgData implements Serializable {
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

    @XStreamAlias("jcmsj")
    private String jcmsj;

    @XStreamAlias("sbtdh")
    private String sbbh;

    @XStreamAlias("zpmc")
    private String zpmc;


    private static JinChuMenImgData getInstance(String jcmsj, String sbtdh,String zpmc, String wxip, String tms,String dbm) {
        JinChuMenImgData data = new JinChuMenImgData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(tms);
        data.setCzdbm(dbm);

        data.setJcmsj(jcmsj);
        data.setSbbh(sbtdh);
        data.setZpmc(zpmc);
        return data;
    }

    public static String getXML(String jcmsj, String sbtdh,String zpmc, String wxip , String tms,String dbm) {
        JinChuMenImgData data = getInstance(jcmsj, sbtdh,zpmc, wxip, tms,dbm);
        return XMLChange.objectToXml(data);
    }
}
