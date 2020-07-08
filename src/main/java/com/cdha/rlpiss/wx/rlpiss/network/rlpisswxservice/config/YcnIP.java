package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 银川南IP
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@Component
@ConfigurationProperties(prefix = "ycn.ip")
@Getter
@Setter
public class YcnIP {
    private String url; //url


    private String shbj;
    private String fhbj;


    private String app;

    private String gzh;

    private String sj;
    private String hz;


    private String addHyxx;
    private String getHyxx;
    private String updateHyxx;
    private String getSyZsXx;
    private String getTxzMl;
    private String getTxzMx;

    private String wxip;

    //    #获取到货需求单
    private String getDhxcxx;
    //#获取发货需求单
    private String getFhXqdxx;

    //    #添加常用车辆
    private String addCyClxx;
    //#查询常用车辆
    private String getCyClxx;
    //#删除常用车辆
    private String delCyClxx;
    //#更新常用车辆
    private String updateCyClxx;

    //    #添加常用车站
    private String addCyCzxx;
    //    #获取常用车站
    private String getCyCzxx;
    //    #删除常用车站
    private String delCyCzxx;
    //#设置默认常用车站
    private String setMrCyCzxx;

    //    #查找司机;
    private String getSjHy;
    //#添加司机好友
    private String addRL_HYHY;
    //#删除司机好友
    private String delRL_HYHY;
    //#获取司机好友
    private String getRL_HYHY;

    //    #货主分享需求单
    private String addRL_YDFX;

    //    #司机查询分享
    private String getRL_YDFX;

    //   #预约进门
    private String yyjm;

    // 预约进门查询

    private String yyjmcx;

    //    #预约进门删除
    private String yyjmzf;

    //    #需求单电商受理
    private String pushxqdsl;

    //    装卸车完成推送
    private String pushzxcwc;


    //    #预约受理推送货主
    private String pushyuyuetohz;
    //#预约受理推送司机
    private String pushyuyuetosj;


    //    #交付完成推送
    private String pushjfwc;
    //#汽车进出门推送
    private String pushjcm;


    //    #获取电商受理需求单详情
    private String pushgetxqd;

    //    #根据发站查询收货人
    private String getShrByFz;
    //#根据发站查询到站名
    private String getDzByFz;
    //#根据到站查询发站
    private String getFzByDz;
    //#根据到站查询托运人
    private String gettyrByDz;

    //评价需求单
    private String pjxqd;


    //    #银川南微信公众号模版id 需求单受理模版ID
    private String mbidxqdsl;
    //#银川南微信公众号模版id 装卸完成模版ID
    private String mbidzxwc;
    //    #银川南微信公众号模版id 交付完成完成模版ID
    private String mbidjfwc;
    //#银川南微信公众号模版id 需求单受理模版ID
    private String mbidyytohz;
    //#银川南微信公众号模版id 需求单受理模版ID
    private String mbidyytosj;
    //#银川南微信公众号模版id 进出门模版ID-进门模版ID
    private String mbidjm;
    //#银川南微信公众号模版id 进出门模版ID-出门模版ID
    private String mbidcm;

    //    #银川南微信公众号模版id 需求单受理模版网页类型标记
    private String mbidxqdsltype;
    //            #银川南微信公众号模版id 装卸完成模版网页类型标记
    private String mbidzxwctype;
    //    #银川南微信公众号模版id 交付完成完成模版ID
    private String mbidjfwctype;
    //            #银川南微信公众号模;版id 进门预约受理推送货主模版网页类型标记
    private String mbidyytohztype;
    //            #银川南微信公众号模版id 进门预约受理推送司机模版网页类型标记
    private String mbidyytosjtype;

    //            #银川南微信公众号模版id 进门模版网页类型标记
    private String mbidjmtype;
    //            #银川南微信公众号模版id 出门模版网页类型标记
    private String mbidcmtype;


    //    #根据推送消息查询推送详情-查询装卸完成详情图片
    private String getZxInfoByPJID;
    //#根据推送消息查询推送详情-查询汽车进出门详情图片
    private String getQcjcmImageInfo;

    //添加常用收发货人
    private String addSfhr;

    //删除常用收发货人
    private String delSfhr;


    //查询常用收发货人

    private String querySfhr;


    //    #货主、代理公司给好友授权
    private String sq;

    //    #货主、代理公司给好友撤销授权
    private String delsq;

    //    #获取集装箱发货清单
    private String getJzxFhQd;

    //    #获取集装箱到货清单
    private String getJzxDhQd;


    //    #//查询集装箱还箱数据
    private String jzxhx;

    //#//查询提前上货托运人信息
    private String tqshtyrxx;

    //#//查询提前上货托运人发到站信息
    private String tqshtyrfdzxx;


}

