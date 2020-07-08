package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;


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
public class ZXImgInfoAqptReData implements Serializable {
    @XStreamAlias("HEAD")
    private Head head;

    @XStreamImplicit(itemFieldName = "ReturnData")
    private List<ReturnData> returnData;

    @Setter
    @Getter
    @ToString
    public class ReturnData implements Serializable {
        @XStreamImplicit(itemFieldName = "ITEM")
        private List<Item> item;
    }

    @Setter
    @Getter
    @ToString
    public class Head implements Serializable {
        @XStreamAlias("FHZBS")
        private String FHZBS;
    }


    @Setter
    @Getter
    @ToString
    public class Item implements Serializable {
        @XStreamAlias("IMG")
        private String img;

        @XStreamAlias("TYPE")
        private String type;


    }

    public static <T> T xmlToObject(String xml, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        //xstream使用注解转换
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xml);
    }

    /**
     * 将bean转换为xml
     *
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