package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 提前上货---获取托运人
 * @Author HJL
 * @Date Created in 2020-06-06
 */
@XStreamAlias("ReturnDataSet")
@Data
public class TqshTyrAqptReData implements Serializable {
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
        @XStreamAlias("CYSFHR")
        private String tyrName;


        @XStreamImplicit(itemFieldName = "ZCRBJH")
        private List<Zcrbhs> tyrInfos;



    }
    @Data
    @ToString
    public class Zcrbhs implements Serializable{
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
