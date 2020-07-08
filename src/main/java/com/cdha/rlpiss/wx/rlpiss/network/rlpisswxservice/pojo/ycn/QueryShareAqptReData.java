package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 司机查询分享结果  返回bean
 * @Author HJL
 * @Date Created in 2020-04-26
 */

@Data
@XStreamAlias("ReturnDataSet")
public class QueryShareAqptReData implements Serializable {
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
    public class ReturnData implements Serializable {
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

        @XStreamAlias("PL")//品类
        private String pl;

        @XStreamAlias("WXOPENID")//品类
        private String wxopenid;


        @XStreamAlias("LX")// //整车到货 0，整车发货1，集装箱到货2，集装箱发货3.
        private String lx;


        @XStreamAlias("YDFZ100")//发货重量
        private String ydfz00;

        @XStreamAlias("YDPMJS1")//发货件数
        private String ydpmjs;

        @XStreamAlias("YDZCRQ0")//发货运单装车日期
        private String ydzcrq;

        @XStreamAlias("NQJHBJ")//到货交付状态
        private String nqjhbj;

        @XStreamAlias("HZ")//到货 货重
        private String hz;

        @XStreamAlias("JS")//到货 件数
        private String js;

        @XStreamAlias("XWSJ")//到货 卸完时间
        private String xwsj;


        @XStreamAlias("FXSJS")
        private FXSJS fxsjs;

        @XStreamAlias("HPH")
        private String hph;


        /**
         * 以下为集装箱分享查询
          */
        @XStreamAlias("YDH")
        private String jzxYdh;

        @XStreamAlias("FZ")
        private String jzxFz;

//                <FZLM>YEJ</FZLM>
        @XStreamAlias("FZLM")
        private String jzxFzDbm;

        @XStreamAlias("FTMS")
        private String jzxFzTMS;

//        <DZ>平湖</DZ>
        @XStreamAlias("DZ")
        private String jzxDz;


//        <DZLM>PHQ</DZLM>
        @XStreamAlias("DZLM")
        private String jzxDzDbm;

        @XStreamAlias("DTMS")
        private String jzxDzTMS;


//        <FHR>河北鸿途物流有限公司</FHR>

        @XStreamAlias("FHR")
        private String jzxFhr;


//        <FHRDH>13519579696</FHRDH>

        @XStreamAlias("FHRDH")
        private String jzxFhrDh;


//        <SHR>深圳市海陆川物流有限公司</SHR>

        @XStreamAlias("SHR")
        private String jzxShr;


//        <SHRDH>13631354040</SHRDH>
        @XStreamAlias("SHRDH")
        private String jzxShrDh;


//        <ZYID>YEJ202005300090</ZYID>
        @XStreamAlias("ZYID")
        private String jzxZyid;


//        <XH_STR>TBJU7239749</XH_STR>

        @XStreamAlias("XH_STR")
        private String jzxXH;


//        <XX>40</XX>
        @XStreamAlias("XX")
        private String jzxXx;


        @XStreamAlias("ZTMC")
        private  String ztmc;//集装箱清单----状态名称
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
