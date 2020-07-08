package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@Data
@XStreamAlias("ReturnDataSet")
public class ShouYeAQPTReData implements Serializable {

    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamAlias( "ReturnData")
    private ReturnData returnData;

    @Data
    public class HEAD implements Serializable {
        @XStreamAlias("FHZBS")
        private String fhzbs;
    }

    @Data
    public class ReturnData implements Serializable {
        @XStreamAlias("jzxz")
        private String jzxz;//进站须知
        @XStreamAlias("blxz")
        private String blxz; //办理须知
        @XStreamAlias("txz")
        private String txz;//停限装
        @XStreamAlias("fwzl")
        private String fwzl;//服务指南
    }
}
