package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-05-08
 */
@Data
@XStreamAlias("ReturnDataSet")
public class CyCzxxQueryAqptReData implements Serializable {
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
        @XStreamAlias("CYCZXXID")
        private String cyczxxid;
        /// <summary>
        /// 电报码
        /// </summary>
        @XStreamAlias("CYCZDBM") //
        private String cyczdbm;
        /// <summary>
        /// tmis
        /// </summary>
        @XStreamAlias("CYCZTMIS") //
        private String cycztmis;
        /// <summary>
        /// 站名称
        /// </summary>
        @XStreamAlias("CYCZMC") //
        private String cyczmc;
        /// <summary>
        /// 地址
        /// </summary>
        @XStreamAlias("CYCZDZ") //
        private String cyczdz;
        /// <summary>
        /// 电话
        /// </summary>
        @XStreamAlias("CYCZDH") //
        private String cyczdh;
        /// <summary>
        /// 联系人名称
        /// </summary>
        @XStreamAlias("CYCZLXRMC") //
        private String cyczlxrmc;
        /// <summary>
        /// 常用车站办理限制
        /// </summary>
        @XStreamAlias("CYCZBLXZ") //
        private String cyczblxz;
        /// <summary>
        /// 微信OPENID
        /// </summary>
        @XStreamAlias("WXOPENID") //
        private String wxopenid;
        /// <summary>
        /// 外键用户ID
        /// </summary>
        @XStreamAlias("YHID") //
        private String yhid;
        /// <summary>
        /// 是否为默认常用车站 0不是 1是';
        /// </summary>
        @XStreamAlias("SFMRCYCZ") //
        private String sfmrcycz;
    }
}
