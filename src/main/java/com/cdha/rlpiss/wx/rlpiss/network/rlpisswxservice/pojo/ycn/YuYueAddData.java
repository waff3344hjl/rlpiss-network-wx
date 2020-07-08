package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 预约请求 bean
 * @Author HJL
 * @Date Created in 2020-04-26
 */
@Data
@XStreamAlias("parameter")
public class YuYueAddData implements Serializable {
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


    @XStreamAlias("CPHLIST")
    private CphList cphs;

    @XStreamAlias("JZXOBJECT")//集装箱对象 2
    private JzxInfoToCar jzxs;

    @XStreamAlias("CLLX")//车辆类型 3
    private String cllx;

    @XStreamAlias("YYRQ")//有效时间 4
    private String yyrq;

    @XStreamAlias("TITLE")//有效标记 5
    private String title;


    @XStreamAlias("JCCZ")//汽车进出车站名 6
    private String jccz;


    @XStreamAlias("JCCZDBM")//进出车站电报码 7
    private String jcczdbm;

    @XStreamAlias("JCCZTMS")//进出车站tims 8
    private String jccztms;

    @XStreamAlias("OPENID")//openid 9
    private String openid;


    @XStreamAlias("SFHBJ")//收发标记 11   ||  收货 =S ； 发货 =F ;仓储 = C
    private String sfhbj;

    @XStreamAlias("ISORDER")// ISORDER 预约是否完成 ----0 = 完成 ；1 ==未完成
    private String isOrder;

    @XStreamAlias("JBRSJH")
    private String jbrsjh;//经办人手机号

    @XStreamAlias("YUNDANHAOS")//openid 9
    private YDHInfos ydhs;

    @Data
    public static class CphList{
        @XStreamImplicit(itemFieldName = "CPHOBJECT")
        private List<CarNos> cphList;
    }

    @Data
    public static class YDHInfos {
        @XStreamImplicit(itemFieldName = "YUNDANHAOINFOLIST")
        List<YDHInfo> ydhslist;
    }

    @Data
    public static class YDHInfo {
        @XStreamAlias("YUNDANHAO")//yundanhao 10
        private String yundanhao;
        //新增
        @XStreamAlias("DFZM")// 到发站   送货 =到站名； 到货：发站名
        private String dfzm;

        @XStreamAlias("FHR")//发货人
        private String fhr;

        @XStreamAlias("SHR") //收货人
        private String shr;

        @XStreamAlias("PL")//品类
        private String pl;

        @XStreamAlias("PM")//品名
        private String pm;
    }


    @Data
    public static class CarNos {
        @XStreamAlias("CPH1")//车牌号1
        private String cph1;
        @XStreamAlias("YYJS1")//预约件数
        private String yyjs1;

        @XStreamAlias("YYHZ1")//预约重量
        private String yyhz1;

        @XStreamAlias("YYSJSJ1")//司机手机号
        private String yysjsj1;

        @XStreamAlias("YYSJXM1")//司机姓名
        private String yysjxm1;

    }

    @Data
    public static class JzxInfoToCar {

        @XStreamAlias("JZJZXXHFORCPH1")//车牌号1对应集装箱info 进站
        private String jzjzx1;
        @XStreamAlias("JZJZXXXFORCPH1")
        private String jzjzxxx1;


    }


    public static YuYueAddData setThis(YuYueAddData data, StationInfo stationInfo, String wxip) {
        YuYueAddData reData = new YuYueAddData();
        reData.setUserid(StringUtils.changNull(data.getOpenid()));
        reData.setUser(StringUtils.changNull(data.getOpenid()));
        reData.setIp(wxip);
        reData.setCztms(stationInfo.getStationTms());
        reData.setCzdbm(stationInfo.getStationDbm());
        reData.setCphs((data.getCphs() == null || data.getCphs().getCphList().size() == 0) ? new CphList() : data.getCphs());

        reData.setJzxs((data.getJzxs() == null ||StringUtils.isEmpty( data.getJzxs().getJzjzx1()) ||StringUtils.isEmpty( data.getJzxs().getJzjzxxx1())) ? setNullJzxInfoToCar() : data.getJzxs());

        reData.setCllx(StringUtils.changNull(data.cllx));
        reData.setYyrq(StringUtils.changNull(data.getYyrq()));
        reData.setTitle("HY_WXYY");
        reData.setJccz(StringUtils.changNull(data.getJccz()));
        reData.setJcczdbm(StringUtils.changNull(data.getJcczdbm()));
        reData.setJccztms(StringUtils.isEmpty(data.getJccztms()) ? "14866" : StringUtils.changNull(data.getJccztms()));


        reData.setSfhbj(StringUtils.changNull(data.getSfhbj()));
        reData.setIsOrder("0");

        //新增
        reData.setYdhs((data.getYdhs() == null || data.getYdhs().getYdhslist().size() == 0) ? setNullYDHInfo() : data.getYdhs());
        reData.setJbrsjh(StringUtils.changNull(data.getJbrsjh()));

        return reData;
    }

    public static String getXML(YuYueAddData data, StationInfo stationInfo, String wxip) {
        YuYueAddData reData = setThis(data, stationInfo, wxip);
        return XMLChange.objectToXml(reData);
    }

    private static YDHInfos setNullYDHInfo() {
        YDHInfo info = new YDHInfo();
        info.setDfzm("");
        info.setFhr("");
        info.setPl("");
        info.setPm("");
        info.setYundanhao("");
        info.setShr("");

        List<YDHInfo> infos = new ArrayList<>();
        infos.add(info);

        YDHInfos infos1 = new YDHInfos();
        infos1.setYdhslist(infos);
        return infos1;
    }

    private static JzxInfoToCar setNullJzxInfoToCar(){
        JzxInfoToCar jzxInfoToCar = new JzxInfoToCar();
        jzxInfoToCar.setJzjzx1("");
        jzxInfoToCar.setJzjzxxx1("");
        return jzxInfoToCar;

    }
}
