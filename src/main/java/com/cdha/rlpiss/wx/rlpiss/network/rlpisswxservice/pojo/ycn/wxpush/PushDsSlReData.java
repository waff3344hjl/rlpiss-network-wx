package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.wxpush;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@XStreamAlias("ReturnDataSet")
public class PushDsSlReData implements Serializable {
    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamImplicit(itemFieldName="ReturnData")
    private List<ReturnData> returnData;


    @Setter
    @Getter
    @ToString
    public class HEAD implements Serializable{
        @XStreamAlias("FHZBS")
        private String FHZBS;

    }

    @Data
    public static class FXSJS {
        @XStreamImplicit(itemFieldName = "FXSJ")//
                List<FXSJSInfo> fxsj;
    }

    @Data
    public static class FXSJSInfo {
        @XStreamAlias("SJH")
        private String sjh;
        @XStreamAlias("YHXM")
        private String yhxm;
        @XStreamAlias("YDYDH00")
        private String ydh;
        @XStreamAlias("CPH")
        private String cph;
    }
    @Setter
    @Getter
    @ToString
    @XStreamAlias("ReturnDataWXGZH")
    public static class ReturnData implements Serializable {
        @XStreamAlias("YDYDH00") //需求单号
        private String ydydhoo;

        @XStreamAlias("YDFZMC0")//发站
        private String ydfzmco;

        @XStreamAlias("YDDZMC0")//到站
        private String yddzmco;

        @XStreamAlias("YDFTMIS")//发站TMS
        private String fztms;

        @XStreamAlias("YDDTMIS")//到站TMS
        private String dztms;

        @XStreamAlias("YDFZDBM")//发站电报码
        private String fzdbm;

        @XStreamAlias("YDDZDBM")//到站电报码
        private String dzdbm;

        @XStreamAlias("TYRTBZLHJ")//重量
        private String ydfz100;

        @XStreamAlias("YDFZ100")//发货重量
        private String ydfz00;

        @XStreamAlias("YDPMJS1")//发货件数
        private String ydpmjs;

        @XStreamAlias("YDZCRQ0")//运单装车日期
        private String ydzcrq;

        @XStreamAlias("PL")//品类
        private String pl;


        @XStreamAlias("YDPMHZ1")//品名
        private String ydpmhz1;

        @XStreamAlias("FHJBRSJ")//发货经办人电话
        private String fhjbrsj;

        @XStreamAlias("SHJBRSJ")//收货经办人电话
        private String shjbrsj;

        @XStreamAlias("YDSHRMC")//收货人
        private String shr;

        @XStreamAlias("YDFHRMC")//发货人
        private String fhr;


        @XStreamAlias("CH")//车号
        private String ch;

        @XStreamAlias("HQHW")//货区货位
        private String hqhw;


        @XStreamAlias("WXOPENID")//品类
        private String wxopenid;


        @XStreamAlias("FXSJS")
        private FXSJS fxsjs;

    }

    public static <T> T xmlToObject(String xml, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        //xstream使用注解转换
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xml);
    }
    /**
     * 将bean转换为xml
     * @param obj 转换的bean
     * @return bean转换为xml
     */
    public static String objectToXml(Object obj) {
        //  XStream xStream = new XStream();
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//解决下划线问题
        //xstream使用注解转换
        xStream.processAnnotations(obj.getClass());
        return xStream.toXML(obj);
    }

}