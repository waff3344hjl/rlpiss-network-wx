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
public class PushJfwcReData implements Serializable {
    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamImplicit(itemFieldName = "ReturnData")
    private List<ReturnData> returnData;

    @Setter
    @Getter
    @ToString
    public class HEAD implements Serializable {
        @XStreamAlias("FHZBS")
        private String fhzbs;
    }

    @Setter
    @Getter
    @ToString
    public class ReturnData implements Serializable {
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
        @XStreamImplicit(itemFieldName = "WXOPENIDS")
        private List<WXopenids> wxopenids;
    }

    @Data
    public static class WXopenids {
        @XStreamAlias("WXOPENID")
        private String wxopenid;
    }
}