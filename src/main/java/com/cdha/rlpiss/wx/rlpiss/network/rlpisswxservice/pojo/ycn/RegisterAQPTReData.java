package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 安全平台返回值基类
 * @Author HJL
 * @Date Created in 2020-04-21
 */

@Data
@XStreamAlias("ReturnDataSet")
public class RegisterAQPTReData implements Serializable {


    @XStreamAlias("HEAD")
    private HEAD head;



    @Data
    public class HEAD implements Serializable {
        @XStreamAlias("FHZBS")
        private String fhzbs;


    }


    @XStreamAlias("ReturnData")
    private String returnData;
}
