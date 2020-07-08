package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 查询常用车辆 -返回体
 * @Author HJL
 * @Date Created in 2020-04-23
 */

@Data
@XStreamAlias("ReturnDataSet")
public class CarQueryAqptReData implements Serializable {
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
        @XStreamAlias("CLXXID") //
        private String clxxID;


        @XStreamAlias("CPH") //
        private String cph;//车牌号

        @XStreamAlias("CLLX") //
        private String cllx;//车辆类型

        @XStreamAlias("ZZ") //
        private String zz;//载重

        @XStreamAlias("WLGS") //
        private String wlgs;//物流公司

        @XStreamAlias("SJH") //
        private String sjh;//手机号

        @XStreamAlias("SJXM") //
        private String sjxm;//司机姓名

        @XStreamAlias("SJSJH") //
        private String sjsjh;//司机手机号

        @XStreamAlias("WXOPENID") //
        private String wxopenid;//微信ID

        @XStreamAlias("YHID") //
        private String yhid;
    }


}
