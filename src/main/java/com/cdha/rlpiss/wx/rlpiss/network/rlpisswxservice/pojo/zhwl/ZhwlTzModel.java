package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 推送信息-----返回实体
 * @Author HJL
 * @Date Created in 2020/7/20
 */
@Data
public class ZhwlTzModel implements Serializable {
    private String sjh;
    private String qymc;
    private String yhlx;
    private String yhid;
    private String yhxm;
    private String wxopenid;

    private String cph;
    private String qshbj;
    private String ysrwid;
    private String qswcsj;
    private String blztmis;
    private String blzmc;
    private String sjxm;
    private String sjsjh;
    private String yslx;

    private List<ZhwlXqdInfo> list;
}
