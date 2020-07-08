package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.CarRequest;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 添加常用车辆  - 请求体
 * @Author HJL
 * @Date Created in 2020-04-23
 */
@Data
@XStreamAlias("parameter")
public class CarAddData implements Serializable {
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

    @XStreamAlias("cph") //
    private String cph;//车牌号

    @XStreamAlias("cllx") //
    private String cllx;//车辆类型

    @XStreamAlias("zz") //
    private String zz;//载重

    @XStreamAlias("wlgs") //
    private String wlgs;//物流公司

    @XStreamAlias("sjh") //
    private String sjh;//手机号

    @XStreamAlias("sjxm") //
    private String sjxm;//司机姓名

    @XStreamAlias("sjsjh") //
    private String sjsjh;//司机手机号

    @XStreamAlias("wxopenid") //
    private String wxopenid;//微信ID

    private static CarAddData setObj(BaseRequestData<CarRequest> info, String wxip, StationInfo stationInfo){
        CarAddData data = new CarAddData();
        data.setUserid("wxid");
        data.setUser("wx");
        data.setIp(wxip);
        data.setCzdbm(stationInfo.getStationDbm() + "");
        data.setCztms(stationInfo.getStationTms() + "");

       String cph = StringUtils.isEmpty(info.getRqData().getCarInfo().getCph()) ? " " :info.getRqData().getCarInfo().getCph();
       String cllx = StringUtils.isEmpty(info.getRqData().getCarInfo().getCllx()) ? " " :info.getRqData().getCarInfo().getCllx();
       String zz = StringUtils.isEmpty(info.getRqData().getCarInfo().getZz()) ? " " :info.getRqData().getCarInfo().getZz();
       String wlgs = StringUtils.isEmpty(info.getRqData().getCarInfo().getWlgs()) ? " " :info.getRqData().getCarInfo().getWlgs();
       String sjh = StringUtils.isEmpty(info.getRqData().getCarInfo().getSjh()) ? " " :info.getRqData().getCarInfo().getSjh();
       String sjxm = StringUtils.isEmpty(info.getRqData().getCarInfo().getSjxm()) ? " " :info.getRqData().getCarInfo().getSjxm();
       String sjsjh = StringUtils.isEmpty(info.getRqData().getCarInfo().getSjsjh()) ? " " :info.getRqData().getCarInfo().getSjsjh();


        data.setCph(cph);
        data.setCllx(cllx);
        data.setZz(zz);
        data.setWlgs(wlgs);
        data.setSjh(sjh);
        data.setSjxm(sjxm);
        data.setSjsjh(sjsjh);

        data.setWxopenid(info.getRqData().getUser().getWxopenid()+"");

        return data;
    }

    public static String getXML(BaseRequestData<CarRequest> data,String wxip, StationInfo stationInfo){

        CarAddData dataXml = setObj(data,wxip,stationInfo);
//
        return XMLChange.objectToXml(dataXml);
    }

}
