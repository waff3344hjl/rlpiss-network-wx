package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 评价需求单
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Data
@XStreamAlias("parameter")
public class PjXqdData implements Serializable {
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

    @XStreamAlias("FZMC")
    private String fzmc; //发站名称
    @XStreamAlias("DZMC")
    private String dzmc; //到站名称
    @XStreamAlias("PMMC")
    private String pmmc;//品名名称
    @XStreamAlias("BYKSSJ")
    private String bykssj;//搬运开始时间
    @XStreamAlias("BYJSSJ")
    private String byjssj;//搬运结束时间
    @XStreamAlias("HQHWMC")
    private String hqhwmc;//获取货位名称
    @XStreamAlias("DJBH")
    private String djbh;//票据编号
    @XStreamAlias("BLZDBM")
    private String blzdbm;//电报码
    @XStreamAlias("BLZMC")
    private String blzmc;//站名-中文
    @XStreamAlias("BLZTMS")
    private String blztms;//车站tms码

    @XStreamAlias("STAR")
    private String star;//几星评价 ---1到5星

    @XStreamAlias("YX")
    private String yx;//评价----影响

    @XStreamAlias("EDIT")
    private String edit;//评价----用户手输入


    public static PjXqdData getInstance(String fzmc, String dzmc, String djbh, String blzdbm, String blzmc, String blztms, String byjssj, String bykssj, String hqhwmc, String pmmc, String startpj, String yx, String edit, String wxip) {
        PjXqdData data = new PjXqdData();

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCztms(blztms);
        data.setCzdbm(blzdbm);

        data.setFzmc(fzmc);
        data.setDzmc(dzmc);
        data.setDjbh(djbh);
        data.setBlzdbm(blzdbm);
        data.setBlzmc(blzmc);
        data.setBlztms(blztms);
        data.setBykssj(bykssj);
        data.setByjssj(byjssj);
        data.setHqhwmc(hqhwmc);
        data.setPmmc(pmmc);
        data.setStar(startpj);
        data.setYx(yx);
        data.setEdit(edit);
        return data;
    }

    public static String getXML(String fzmc, String dzmc, String djbh, String blzdbm, String blzmc, String blztms, String byjssj, String bykssj, String hqhwmc, String pmmc, String startpj, String yx, String edit, String wxip) {
        PjXqdData data = getInstance(fzmc, dzmc, djbh, blzdbm, blzmc, blztms, byjssj, bykssj, hqhwmc, pmmc, startpj, yx, edit, wxip);
        return XMLChange.objectToXml(data);
    }
}
