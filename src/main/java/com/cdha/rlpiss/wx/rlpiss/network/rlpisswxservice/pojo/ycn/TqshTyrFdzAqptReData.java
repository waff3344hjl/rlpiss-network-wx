package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 提前上货 --获取发到站
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@XStreamAlias("ReturnDataSet")
@Data
public class TqshTyrFdzAqptReData implements Serializable {
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


        @XStreamAlias("HZPM")
        private String hzpm;

        @XStreamAlias("FHDWMC")
        private String fhdwmc;

        @XStreamAlias("SHDWMC")
        private String shdwmc;

        @XStreamAlias("DZHZZM")
        private String dzmc;

        @XStreamAlias("DZHZZM1")
        private String  dzhzzm1;

    }
}
