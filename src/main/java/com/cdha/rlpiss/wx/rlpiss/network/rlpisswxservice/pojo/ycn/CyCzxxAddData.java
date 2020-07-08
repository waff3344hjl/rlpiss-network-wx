package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-05-08
 */
@Data
@XStreamAlias("parameter")
public class CyCzxxAddData implements Serializable {
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



    @XStreamAlias("cyczxxid") //
    private String cyczxxid;
    /// <summary>
    /// 电报码
    /// </summary>
    @XStreamAlias("cyczdbm") //
    private String cyczdbm;
    /// <summary>
    /// tmis
    /// </summary>
    @XStreamAlias("cycztmis") //
    private String cycztmis ;
    /// <summary>
    /// 站名称
    /// </summary>
    @XStreamAlias("cyczmc") //
    private String cyczmc ;
    /// <summary>
    /// 地址
    /// </summary>
    @XStreamAlias("cyczdz") //
    private String cyczdz ;
    /// <summary>
    /// 电话
    /// </summary>
    @XStreamAlias("cyczdh") //
    private String cyczdh ;
    /// <summary>
    /// 联系人名称
    /// </summary>
    @XStreamAlias("cyczlxrmc") //
    private String cyczlxrmc;
    /// <summary>
    /// 常用车站办理限制
    /// </summary>
    @XStreamAlias("cyczblxz") //
    private String cyczblxz;
    /// <summary>
    /// 微信OPENID
    /// </summary>
    @XStreamAlias("wxopenid") //
    private String wxopenid ;
    /// <summary>
    /// 外键用户ID
    /// </summary>
    @XStreamAlias("yhid") //
    private String yhid ;
    /// <summary>
    /// 是否为默认常用车站 0不是 1是';
    /// </summary>
    @XStreamAlias("sfmrcycz") //
    private String sfmrcycz ;





    public static CyCzxxAddData getInstance(CyCzxxAddData dataOld, User user, String wxip, StationInfo stationInfo){
        CyCzxxAddData data = new CyCzxxAddData();

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(stationInfo.getStationDbm() + "");
        data.setCztms(stationInfo.getStationTms() + "");
        data.setCyczdbm(StringUtils.changNull(dataOld.getCyczdbm()));
        data.setCycztmis(StringUtils.changNull(dataOld.getCycztmis()));
        data.setCyczmc(StringUtils.changNull(dataOld.getCyczmc()));
        data.setCyczdz(StringUtils.changNull(dataOld.getCyczdz()));
        data.setCyczdh(StringUtils.changNull(dataOld.getCyczdh()));
        data.setCyczlxrmc(StringUtils.changNull(dataOld.getCyczlxrmc()));
        data.setCyczblxz(StringUtils.changNull(dataOld.getCyczblxz()));
        data.setCyczxxid(StringUtils.changNull(dataOld.getCyczxxid()));
        data.setSfmrcycz("0");//不设置默认
        data.setWxopenid(StringUtils.changNull(user.getWxopenid()));
        data.setYhid(StringUtils.changNull(user.getYhid()));

        return  data;
    }

    public static String getXML (CyCzxxAddData rqData, User user, String wxip, StationInfo stationInfo){
        CyCzxxAddData data = getInstance(rqData,user,wxip,stationInfo);
        return XMLChange.objectToXml(data);
    }

    public static CyCzxxAddData getInstance2(CyCzxxAddData dataOld, User user, String wxip, StationInfo stationInfo){
        CyCzxxAddData data = new CyCzxxAddData();

        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(stationInfo.getStationDbm() + "");
        data.setCztms(stationInfo.getStationTms() + "");


        data.setCyczxxid(dataOld.getCyczxxid()+"");
        data.setWxopenid(user.getWxopenid());

        return  data;
    }
    public static String getXML2 (CyCzxxAddData rqData, User user, String wxip, StationInfo stationInfo){
        CyCzxxAddData data = getInstance2(rqData,user,wxip,stationInfo);
        return XMLChange.objectToXml(data);
    }
}
