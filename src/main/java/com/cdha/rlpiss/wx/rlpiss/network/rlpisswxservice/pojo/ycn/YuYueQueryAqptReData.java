package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@Data
@XStreamAlias("ReturnDataSet")
public class YuYueQueryAqptReData implements Serializable {
    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamImplicit(itemFieldName = "ReturnData")
    private List<ReturnData> returnData;


    @Data
    public class HEAD implements Serializable {
        @XStreamAlias("FHZBS")
        private String FHZBS;
    }

    @Data
    public class ReturnData implements Serializable {
        @XStreamAlias("CZBH") //成组编号
        private String bzid;

        @XStreamAlias("CPH")
        private String cph;

        @XStreamAlias("YYRQ")
        private String yxrq;
        @XStreamAlias("BLZMC")
        private String blzmc;
        @XStreamAlias("JZXLX")
        private String jzxlx;
        @XStreamAlias("JMJZXH")
        private String jmjzxh;
        @XStreamAlias("CMJZXH")
        private String cmjzxh;

        @XStreamAlias("XQDH")
        private String xqdh;

        private List<String> xqdhs;


        @XStreamAlias("CLLX")
        private String cllx;//1.普通卡车 2.集装箱卡车

        @XStreamAlias("QCJMTSBZ")
        private String qcjmtsbz;

        @XStreamAlias("QCJMSJ")//汽车进门时间
        private String qcjmsj;

        @XStreamAlias("QCCMSJ")//汽车出门视觉
        private String qccmsj;

        @XStreamAlias("YYJS")//预约件数
        private String yyjs;

        @XStreamAlias("YYHZ")//yyhz货重
        private String yyhz;

    }
}
