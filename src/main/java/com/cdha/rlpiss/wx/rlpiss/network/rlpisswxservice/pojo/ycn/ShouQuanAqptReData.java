package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 用户授权 ---安全平台返回体
 * @Author HJL
 * @Date Created in 2020-05-21
 */
@Data
@XStreamAlias("ReturnDataSet")
public class ShouQuanAqptReData  implements Serializable {
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

    }
}
