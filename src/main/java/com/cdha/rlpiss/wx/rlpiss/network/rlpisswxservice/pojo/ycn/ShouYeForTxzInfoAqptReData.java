package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 首页 获取停限装详情 返回体
 * @Author HJL
 * @Date Created in 2020-04-23
 */
@Data
@XStreamAlias("ReturnDataSet")
public class ShouYeForTxzInfoAqptReData implements Serializable {
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

        @XStreamAlias("mxid")
        private String mxid;


        @XStreamAlias("mlid")
        private String mlid;


        @XStreamAlias("txz")
        private String txz;


    }
}
