package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.img_service.JcmImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description 进出门图片查询
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@RestController
@Slf4j
@RequestMapping("/jcmimg")
public class JcmImgController {

    @Autowired
    JcmImgService service;
    /**
     *
     * @param tms 站场TMS码
     * @param jcmsj  进出门时间
     * @param sbbh  设备通道号
     * @param zpmc 照片名称
     * @param dbm 电报码
     * @return 1
     * @throws IOException 1
     */
    @RequestMapping(value = "/get_image_jcm")
    @CrossOrigin
    public BaseResponseData<List<String>> getImageJCM(@RequestParam(value = "TMS", required = false) String tms,
                                                      @RequestParam(value = "JCMSJ", required = false) String jcmsj,
                                                      @RequestParam(value = "SBBH", required = false) String sbbh,
                                                      @RequestParam(value = "ZPMC", required = false) String zpmc,
                                                      @RequestParam(value = "DBM", required = false) String dbm) throws IOException {
       return service.getImageJCM(tms,jcmsj,sbbh,zpmc,dbm);
    }
}
