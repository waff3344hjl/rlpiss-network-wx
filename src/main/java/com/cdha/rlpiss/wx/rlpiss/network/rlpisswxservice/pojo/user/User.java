package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.LoginAQPTReData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户model
 */
@Data
public class User implements Serializable {


    private String yhid;

    private String yhxm;

    private String sjh;

    private String zjh;

    private String yx;

    private String sfzh;

    private String txdz;

    private String dlmm;

    private String yhlx;

    private Date zcsj;

    private String wxopenid;

    private String sfhr;

    private String dxyzm;

    private Date dxyxsj;

    private String cycz;

    private String sqzt;//授权状态  0 = 授权 ； 1 =未授权

    private List<LoginAQPTReData.ClxxInfo> clxxInfos;





    public static User setUserBySJH(String sjh) {
        User user = new User();
        user.setSjh(sjh);
        return user;
    }

    public static User setUserByOpenid(String openid) {
        User user = new User();
        user.setWxopenid(openid);
        return user;
    }

    public static User setUserByOpenidAndSjh(String openid, String sjh) {
        User user = new User();
        user.setWxopenid(openid);
        user.setSjh(sjh);
        return user;
    }


}
