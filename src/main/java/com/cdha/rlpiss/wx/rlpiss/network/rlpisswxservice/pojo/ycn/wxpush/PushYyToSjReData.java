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
public class PushYyToSjReData implements Serializable {
    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamImplicit(itemFieldName = "ReturnData")
    private List<ReturnData> returnData;


    @Setter
    @Getter
    @ToString
    public class HEAD implements Serializable {
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


        @XStreamAlias("XQDH")
        private String xqdh;


        @XStreamAlias("YYRQ")
        private String yyrq;


        @XStreamAlias("YYSJSJ")
        private String yysjsj;


        @XStreamAlias("YYSJXM")
        private String yysjxm;


        @XStreamAlias("YYHQMC")
        private String yyhqmc;


        @XStreamAlias("WXYYID")
        private String wxyyid;

        @XStreamAlias("FHR")
        private String fhr;

        @XStreamAlias("SHR")
        private String shr;

        @XStreamAlias("JCCZ")
        private String jccz;

        @XStreamImplicit(itemFieldName = "WXOPENIDS")
        private List<WXopenids> wxopenids;


        @XStreamAlias("JBRSJH")
        private String jbrsj;


        @XStreamAlias("CZBH") //成组编号
        private String bzid;

        @XStreamAlias("CZBZ")
        private String   czbz;

        private List<String> xqdhs;

    }

    @Data
    public static class WXopenids {
        @XStreamAlias("WXOPENID")
        private String wxopenid;
    }

}