package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 评价需求单返回体
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@Data
@XStreamAlias("ReturnDataSet")
public class PjXqdAqptReData implements Serializable {
    @XStreamAlias("HEAD")
    private Head head;

    @XStreamImplicit(itemFieldName = "ReturnData")
    private List<ReturnData> returnData;


    @Data
    public class Head {
        @XStreamAlias("FHZBS")
        private String fhzbs;
    }

    @Data
    public class ReturnData {
        @XStreamAlias("CODE")
        private String code;
    }
}
