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
public class SfhrQueryAqptReData implements Serializable{
    @XStreamAlias("HEAD")
    private HEAD head;

    @XStreamImplicit(itemFieldName="ReturnData")
    private List<ReturnData> returnData;


@Data
    public class HEAD implements Serializable {
        @XStreamAlias("FHZBS")
        private String FHZBS;
    }
    @Data
    public class ReturnData implements Serializable{
        @XStreamAlias("CYSFHR")
        private String cysfhr;

        @XStreamAlias("CYSFHRID")
        private String cysfhrID;

        @XStreamAlias("CZDBM")
        private String czdbm;

        @XStreamAlias("CZTMIS")
        private String cztmis;

        @XStreamAlias("CZMC")
        private String czmc;

        @XStreamAlias("DZ")
        private String dz;

        @XStreamAlias("DH")
        private String dh;

        @XStreamAlias("WXOPENID")
        private String wxopenid;

        @XStreamAlias("YHID")
        private String yhid;




    }
}
