package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description Description
 * @Author HJL
 * @Date Created in 2020/7/17
 */
@Data
public class ZxwlServicePath implements Serializable {
    private List<CzTms> tmsList;
    private String wlUrl;
    private String jsqUrl;
    private String hyzUrl;
    private String fubzNo;

    @Data
    public static class CzTms{
        private String tmis;
    }
}

