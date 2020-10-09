package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
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

        @XStreamAlias("CZBH") //成组编号
        private String bzid;

        @XStreamAlias("CZBZ")
        private String   czbz;

        private List<String> xqdhs;


        @XStreamAlias("JBRSJH")
        private String jbrsj;

        @XStreamImplicit(itemFieldName = "WXOPENIDS")
        private List<WXopenids> wxopenids;


    }

    @Data
    public static class WXopenids {
        @XStreamAlias("WXOPENID")
        private String wxopenid;
    }

}