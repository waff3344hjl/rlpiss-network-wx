package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 银川南  获取用户信息
 * @Author HJL
 * @Date Created in 2020-04-21
 */


@Data
@XStreamAlias("ReturnDataSet")
public class LoginAQPTReData implements Serializable {

    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamImplicit(itemFieldName = "ReturnData")
    private List<ReturnData> returnData;

    @Data
    public class HEAD implements Serializable {
        @XStreamAlias("FHZBS")
        private String fhzbs;
    }


    @Data
    public static class ClxxInfo implements Serializable{
        @XStreamAlias("CLXXID") //
        private String clxxid;//id

        @XStreamAlias("CPH") //
        private String cph;//车牌号

        @XStreamAlias("CLLX") //
        private String cllx;//车辆类型

        @XStreamAlias("ZZ") //
        private String zz;//载重

        @XStreamAlias("WLGS") //
        private String wlgs;//物流公司
    }


    @Data
    public class ReturnData implements Serializable {
        @XStreamAlias("YHID") //
        private String yhid;

        @XStreamAlias("YHXM") //
        private String yhxm;

        @XStreamAlias("SJH") //
        private String sjh;

        @XStreamAlias("ZJH") //
        private String zjh;

        @XStreamAlias("YX") //
        private String yx;

        @XStreamAlias("TXDZ") //
        private String txdz;

        @XStreamAlias("DLMM") //
        private String dlmm;

        @XStreamAlias("YHLX") //
        private String yhlx;

        @XStreamAlias("ZCSJ") //
        private String zcsj;

        @XStreamAlias("WXOPENID") //
        private String wxopenid;

        @XStreamAlias("SFHR") //
        private String sfhr;

        @XStreamAlias("CYCZ") //
        private String cycz;

        @XStreamAlias("SQZT") // 授权状态  0 = 未授权 ； 1 =授权
        private String sqzt;


        @XStreamImplicit(itemFieldName = "CYCLS")
        private List<ClxxInfo> clxxInfos;


//        @XStreamAlias("CLXXID") //
//        private String clxxid
    }

    public static LoginAQPTReData getObjByXml(String xml) {
        return XMLChange.xmlToObject(xml, LoginAQPTReData.class);

    }

    public static User getUser(String xml) {
        LoginAQPTReData data = getObjByXml(xml);
        if (data.getHead().getFhzbs().equals("0")) {
            List<ReturnData> li = data.getReturnData();
            User user = new User();
            if (li != null && li.size() > 0) {
                ReturnData ll = li.get(0);
                setUser(ll, user);
            }

            return user;
        } else {
            return null;
        }

    }

    public static List<User> getFrends(String xml) {
        List<User> frends = new ArrayList<>();
        LoginAQPTReData data = getObjByXml(xml);
        if (data.getHead().getFhzbs().equals("0")) {
            List<ReturnData> li = data.getReturnData();
            if (li != null && li.size() > 0) {
                for (ReturnData ll : li
                ) {
                    User user = new User();
                    setUser(ll, user);
                    frends.add(user);
                }
            }
        }
        return frends;
    }

    private static void setUser(ReturnData ll, User user) {
        user.setCycz(ll.getCycz() + "");
        user.setSjh(ll.getSjh() + "");
        user.setSfzh(ll.getSfhr() + "");
        user.setDlmm(ll.getDlmm() + "");
        user.setSfhr(ll.getSfhr() + "");
        user.setTxdz(ll.getTxdz() + "");
        user.setYhlx(ll.getYhlx() + "");
        user.setYhxm(ll.getYhxm() + "");
        user.setYx(ll.getYx() + "");
        user.setYhid(ll.getYhid() + "");
        user.setWxopenid(ll.getWxopenid() + "");
        user.setSqzt(StringUtils.changNull(ll.getSqzt()));

//        user.setSqzt("1"); //测试 已授权
        user.setClxxInfos(ll.getClxxInfos()==null ? new ArrayList<>():ll.getClxxInfos());
    }
}
