package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.wxpush;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@XStreamAlias("ReturnDataSet")
public class PushQcJcmReData implements Serializable {
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
        @XStreamAlias("CPH")
        private String cph;

        @XStreamAlias("WXYYID")
        private String wxyyid;

        @XStreamAlias("JCCZ")
        private String jccz;

        @XStreamAlias("XQDH")
        private String xqdh;

        @XStreamAlias("QCYYID")
        private String qcyyid;

        @XStreamAlias("TMBH")
        private String tmbh;

        @XStreamAlias("BLZTMS")
        private String blztms;

        @XStreamAlias("JMRQSJ")
        private String jmrqsj;

        @XStreamAlias("CMRQSJ")
        private String cmrqsj;

        @XStreamAlias("JCMBZ")
        private String jcmbj;

        @XStreamAlias("QCJCMTSBZ")
        private String qcjcmtsbz;

        @XStreamAlias("HQHW")
        private String hqhw;

        /*需要一个取货发货标记*/
        @XStreamAlias("ZXBJ")
        private String sfbj;

        @XStreamAlias("SBBH")
        private String sbbh;

        @XStreamAlias("JKWJCHPJM")
        private String jkwjchpjm;

        @XStreamAlias("JKWJCHPCM")
        private String jkwjchpcm;


        @XStreamAlias("PZ")//皮重
        private String pz;

        @XStreamAlias("PZGBSJ")//皮重过磅视觉
        private String pzTime;

        @XStreamAlias("MZ")//毛重
        private String mz;

        @XStreamAlias("MZGBSJ")//毛重过磅时间
        private String mzTime;


        @XStreamAlias("JZ")//毛重过磅时间
        private String jz;

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