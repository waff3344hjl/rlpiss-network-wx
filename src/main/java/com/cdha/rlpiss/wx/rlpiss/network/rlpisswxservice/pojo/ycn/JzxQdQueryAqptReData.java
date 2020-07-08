package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 集装箱清单查询--返回体
 * @Author HJL
 * @Date Created in 2020-05-20
 */
@XStreamAlias("ReturnDataSet")
@Data
public class JzxQdQueryAqptReData implements Serializable {
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
    @ToString
    public class ReturnData implements Serializable {
//                <YDH>148662020052050080</YDH> 运单号
        @XStreamAlias("YDH")
        private String ydh;

//        <FZ>银川南</FZ>
        @XStreamAlias("FZ")//发站
        private String fz ;
//        <FZLM>YEJ</FZLM>// 发站电报码
        @XStreamAlias("FZLM")
        private String fzlm;
        @XStreamAlias("FTMS")//发 TMS
        private String ftms;
//        <DZ>平湖(广)</DZ> 到站
        @XStreamAlias("DZ")
        private String dz;
//        <DZLM>PHQ</DZLM> 到站电报码
        @XStreamAlias("DZLM")
        private String dzlm;
        @XStreamAlias("DTMS")
        private String dtms;
//        <FHR>河北鸿途物流有限公司</FHR> 发货人
        @XStreamAlias("FHR")
        private String fhr;
//        <FHRDH>18080890336</FHRDH>
        @XStreamAlias("FHRDH")
        private String fhrdh;
//        <SHR>深圳市海陆川物流有限公司</SHR>
        @XStreamAlias("SHR")
        private String shr;
//        <SHRDH>13631354040</SHRDH>
        @XStreamAlias("SHRDH")
        private String shrdh;
//        <ZYID>YEJ202005200552</ZYID>
        @XStreamAlias("ZYID")
        private String zyid;
//        <XH_STR>TBJU7510423</XH_STR> 箱号
        @XStreamAlias("XH_STR")
        private String jzxxhs;

        @XStreamAlias("XX")
        private String jzxxx;

        private List<String>  jzxList;

        @XStreamAlias("ZTMC")
        private  String ztmc;//集装箱清单----状态名称

        @XStreamAlias("FXSJS")//已分享用户列表
        private FXSJS fxsjs;

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
        @XStreamImplicit(itemFieldName = "CPH")
        private List<String> cph;
    }
}
