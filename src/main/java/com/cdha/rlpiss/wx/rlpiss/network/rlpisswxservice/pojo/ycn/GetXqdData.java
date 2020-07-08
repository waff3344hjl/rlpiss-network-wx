package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 银川南获取需求单信息===参数Bean
 * @Author HJL
 * @Date Created in 2020-04-22
 */
@Data
@XStreamAlias("parameter")
public class GetXqdData implements Serializable {
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

    @XStreamAlias("sjh") //发货是传入发货经办人手机号
    private String fhjbrsjh;


    @XStreamAlias("shjbrsj") // 到货时候。传入收货经办人手机号
    private String shjbrsj;


    @XStreamAlias("ydhs") //
    private String yhds;

    @XStreamAlias("wxopenid") //
    private String wxopenid;


    public static GetXqdData setThis(String wxopenid,String sjh, String shjbrsj, String czdbm, String cztms,String wxip) {
        GetXqdData data = new GetXqdData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(czdbm + "");
        data.setCztms(cztms + "");
        data.setYhds("");
        data.setWxopenid(StringUtils.changNull(wxopenid));
        if (!StringUtils.isEmpty(sjh)) {
            data.setFhjbrsjh(sjh);
        }
        if (!StringUtils.isEmpty(shjbrsj)) {
            data.setShjbrsj(shjbrsj);
        }
        return data;

    }

    public static String getXml(String wxopenid,String sjh, String shjbrsj, String czdbm, String cztms,String wxip) {
        GetXqdData data = setThis(wxopenid,sjh, shjbrsj, czdbm, cztms,wxip);
        return XMLChange.objectToXml(data);
    }
}
