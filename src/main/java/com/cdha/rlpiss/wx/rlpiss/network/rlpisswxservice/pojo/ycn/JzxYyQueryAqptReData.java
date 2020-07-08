package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 集装箱预约前，查询收发人、到发站  返回体
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Data
@XStreamAlias("ReturnDataSet")
public class JzxYyQueryAqptReData implements Serializable{
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

        @XStreamAlias("FZM")
        private String fz;
        @XStreamAlias("DZM")
        private String dz;
        @XStreamAlias("SHR")
        private String shr;
        @XStreamAlias("TYR")
        private String tyr;

    }
}
