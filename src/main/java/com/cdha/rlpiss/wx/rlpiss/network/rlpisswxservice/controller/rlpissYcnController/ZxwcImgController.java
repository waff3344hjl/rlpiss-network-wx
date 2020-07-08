package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.img_service.ZxwcImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description 装卸完成图片查询
 * @Author HJL
 * @Date Created in 2020-04-27
 */
@RestController
@Slf4j
@RequestMapping("/zxwcimg")
public class ZxwcImgController {
    @Autowired
    ZxwcImgService service;

    /**
     *  推送模版-装卸完成--查看详情
     * @param tms 车站tms码
     * @param dbm 车站电报码
     * @param pjid 需求单号
     * @param type 装卸类型---装车 =Z ；卸车=X
     * @return 1
     * @throws IOException 1
     */
    @RequestMapping(value = "/get_image_zxwc")
    @CrossOrigin
    public BaseResponseData<List<String>> getImageZxwc(@RequestParam(value = "CZTMS", required = false) String tms,
                                                       @RequestParam(value = "CZDBM", required = false) String dbm,
                                                       @RequestParam(value = "PJID", required = false) String pjid,
                                                       @RequestParam(value = "TYPE", required = false) String type ) throws IOException {
        return service.getImageZxwc(tms, dbm, pjid, type);
    }
}
