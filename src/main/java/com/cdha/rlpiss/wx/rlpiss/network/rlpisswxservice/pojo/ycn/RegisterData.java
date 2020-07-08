package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserRegister;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 银川南---注册请求体
 * @Author HJL
 * @Date Created in 2020-04-21
 */
@Data
@XStreamAlias("parameter")
public class RegisterData implements Serializable {
    @XStreamAlias("userid") //
    private String userid;

    @XStreamAlias("user") //
    private String user;

    @XStreamAlias("ip") //
    private String ip;

    @XStreamAlias("cztms") //
    private String cztms;

    @XStreamAlias("czdbm") //
    private String czdbm;

    @XStreamAlias("cycz") //常用车站
    private String cycz;

    @XStreamAlias("dlmm") //
    private String dlmm;

    @XStreamAlias("dxyxsj") //
    private String dxyxsj;

    @XStreamAlias("dxyzm") //
    private String dxyzm;

    @XStreamAlias("sfhr") //收发货人
    private String sfhr;

    @XStreamAlias("sjh") //手机号
    private String sjh;

    @XStreamAlias("txdz") //
    private String txdz;

    @XStreamAlias("wxopenid") //微信ID
    private String wxopenid;

    @XStreamAlias("yhid") //
    private String yhid;

    @XStreamAlias("yhlx") //
    private String yhlx;

    @XStreamAlias("yhxm") //
    private String yhxm;

    @XStreamAlias("yx") //
    private String yx;

    @XStreamAlias("zjh") //
    private String zjh;


    //司机信息
    @XStreamAlias("cph") //
    private String cph;//车牌号

    @XStreamAlias("cllx") //
    private String cllx;//车辆类型

    @XStreamAlias("zz") //
    private String zz;//载重

    @XStreamAlias("wlgs") //
    private String wlgs;//物流公司

//    @XStreamAlias("sjh") //
//    private String sjh;//手机号

    @XStreamAlias("sjxm") //
    private String sjxm;//司机姓名

    @XStreamAlias("sjsjh") //
    private String sjsjh;//司机手机号


    public static RegisterData setNewRegisterData(BaseRequestData<UserRegister> baseRequestData, String wxip) {


        RegisterData data = new RegisterData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(baseRequestData.getRqData().getStationInfo().getStationDbm() + "");
        data.setCztms(baseRequestData.getRqData().getStationInfo().getStationTms() + "");

        if (!StringUtils.isEmpty(baseRequestData.getRqData().getCycz())) {
            data.setCycz(baseRequestData.getRqData().getCycz());
        } else {
            data.setCycz("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getSjh())) {
            data.setDlmm(baseRequestData.getRqData().getSjh());
        } else {
            data.setDlmm("");
        }

            data.setDxyxsj("");

        if (!StringUtils.isEmpty(baseRequestData.getRqData().getDxyzm())) {
            data.setDxyzm(baseRequestData.getRqData().getDxyzm());
        } else {
            data.setDxyzm("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getSfhr())) {
            data.setSfhr(baseRequestData.getRqData().getSfhr());
        } else {
            data.setSfhr("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getSjh())) {
            data.setSjh(baseRequestData.getRqData().getSjh());
        } else {
            data.setSjh("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getTxdz())) {
            data.setTxdz(baseRequestData.getRqData().getTxdz());
        } else {
            data.setTxdz("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getWxopenid())) {
            data.setWxopenid(baseRequestData.getRqData().getWxopenid());
        } else {
            data.setWxopenid("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getYhid())) {
            data.setYhid(baseRequestData.getRqData().getYhid());
        } else {
            data.setYhid("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getYhlx())) {
            data.setYhlx(baseRequestData.getRqData().getYhlx());
        } else {
            data.setYhlx("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getYhxm())) {
            data.setYhxm(baseRequestData.getRqData().getYhxm());
        } else {
            data.setYhxm("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getYx())) {
            data.setYx(baseRequestData.getRqData().getYx());
        } else {
            data.setYx("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getRqData().getZjh())) {
            data.setZjh(baseRequestData.getRqData().getZjh());
        } else {
            data.setZjh("");
        }
        if (baseRequestData.getRqData().getCarInfo() != null) {
            if (!StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getCph())) {
                data.setCph(baseRequestData.getRqData().getCarInfo().getCph());
            } else {
                data.setCph("");
            }

            if (!StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getCllx())) {
                data.setCllx(baseRequestData.getRqData().getCarInfo().getCllx());
            } else {
                data.setCllx("");
            }

            if (!StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getWlgs())) {
                data.setWlgs(baseRequestData.getRqData().getCarInfo().getWlgs());
            } else {
                data.setWlgs("");
            }

            if (!StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getZz())) {
                data.setZz(baseRequestData.getRqData().getCarInfo().getZz());
            } else {
                data.setZz("");
            }

            if (!StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getSjxm())) {
                data.setSjxm(baseRequestData.getRqData().getCarInfo().getSjxm());
            } else {
                data.setSjxm("");
            }

            if (!StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getSjsjh())) {
                data.setSjsjh(baseRequestData.getRqData().getCarInfo().getSjsjh());
            } else {
                data.setSjsjh("");
            }
        }


        return data;
    }


    public static RegisterData setNewRegisterData(User baseRequestData, StationInfo stationInfo, String wxip) {

        RegisterData data = new RegisterData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(stationInfo.getStationDbm() + "");
        data.setCztms(stationInfo.getStationTms() + "");

        if (!StringUtils.isEmpty(baseRequestData.getCycz())) {
            data.setCycz(baseRequestData.getCycz());
        } else {
            data.setCycz("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getSjh())) {
            data.setDlmm(baseRequestData.getSjh());
        } else {
            data.setDlmm("");
        }

        data.setDxyxsj("");

        if (!StringUtils.isEmpty(baseRequestData.getDxyzm())) {
            data.setDxyzm(baseRequestData.getDxyzm());
        } else {
            data.setDxyzm("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getSfhr())) {
            data.setSfhr(baseRequestData.getSfhr());
        } else {
            data.setSfhr("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getSjh())) {
            data.setSjh(baseRequestData.getSjh());
        } else {
            data.setSjh("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getTxdz())) {
            data.setTxdz(baseRequestData.getTxdz());
        } else {
            data.setTxdz("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getWxopenid())) {
            data.setWxopenid(baseRequestData.getWxopenid());
        } else {
            data.setWxopenid("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getYhid())) {
            data.setYhid(baseRequestData.getYhid());
        } else {
            data.setYhid("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getYhlx())) {
            data.setYhlx(baseRequestData.getYhlx());
        } else {
            data.setYhlx("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getYhxm())) {
            data.setYhxm(baseRequestData.getYhxm());
        } else {
            data.setYhxm("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getYx())) {
            data.setYx(baseRequestData.getYx());
        } else {
            data.setYx("");
        }
        if (!StringUtils.isEmpty(baseRequestData.getZjh())) {
            data.setZjh(baseRequestData.getZjh());
        } else {
            data.setZjh("");
        }


                data.setCph("");



                data.setCllx("");



                data.setWlgs("");



                data.setZz("");



                data.setSjxm("");



                data.setSjsjh("");




        return data;
    }
}
