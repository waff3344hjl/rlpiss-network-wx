package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 集装箱还箱 ---返回
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@XStreamAlias("ReturnDataSet")
@Data
public class JzxhxQueryAqptReData  implements Serializable {
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

        @XStreamAlias("JMJZXH")
        private String jzxxh;

        @XStreamAlias("JMJZXXX")
        private String jzxxx;

        @XStreamAlias("DZHZZM1")
        private String  dzhzzm1;


    }
}
