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
public class PushZxWcReData implements Serializable {
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
    @Setter
    @Getter
    @ToString
    @XStreamAlias("ReturnDataWXGZH")
    public class ReturnData implements Serializable {
        /* 少了 发到站 */

        @XStreamAlias("CH")
        private String ch;//车号

        @XStreamAlias("PJID")
        private String pjid;//票据ID

        @XStreamAlias("YDH")
        private String ydh;//运单号 暂时没用此参数

        @XStreamAlias("FHR")
        private String fhr;//发货人

        @XStreamAlias("SHR")
        private String shr;//收货人


        @XStreamAlias("HWMC")
        private String hwmc;//货位名称


        @XStreamAlias("ZXKSSJ")
        private String zxkssj;//装卸开始时间

        @XStreamAlias("ZXJSSJ")
        private String zxjssj;//装卸结束时间

        @XStreamAlias("FHRPHONE")
        private String fhrphone;//发货人电话

        @XStreamAlias("SHRPHONE")
        private String shrphone;//收货人电话


        @XStreamAlias("ZXBZ")
        private String zxbz;//装卸标记

        @XStreamAlias("FZM")
        private String fzmc;//发站名称

        @XStreamAlias("DZM")
        private String dzmc;//到站名称

        @XStreamAlias("FZH")
        private String fztms;//发站电报码

        @XStreamAlias("DZH")
        private String dztms;//到站电报码

        @XStreamAlias("PM")
        private String pm;//品名

        @XStreamAlias("HZ")
        private String hz;//货重

        @XStreamImplicit(itemFieldName="WXOPENIDS")
        private List<WXopenids> wxopenids;

    }
    @Data
    public static class WXopenids{
        @XStreamAlias("WXOPENID")
        private String wxopenid;
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