package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.rlpissYcnController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.pjxqd.IPJxqdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 评价需求单
 * @Author HJL
 * @Date Created in 2020-04-29
 */
@RestController
@Slf4j
@RequestMapping("/pj")
public class PJxqdController {

    @Autowired
    IPJxqdService service;

    /**
     * 上传评价信息
     *
     * @param
     * @param fzmc    发站
     * @param dzmc    到站
     * @param djbh    需求单号
     * @param blzdbm  电报码
     * @param blzmc   站名
     * @param blztms  tms
     * @param byjssj  搬运结束时间
     * @param bykssj  搬运开始时间
     * @param hqhwmc  货区货位名称
     * @param pmmc    品名
     * @param startpj 评价--几星
     * @param yx      评价---印象
     * @param edit    评价 --输入
     * @return data
     */
    @CrossOrigin
    @RequestMapping(path = "/setPj")
    public BaseResponseData<List<String>> setPj(@RequestParam(value = "fzmc", required = false) String fzmc,
                                                @RequestParam(value = "dzmc", required = false) String dzmc,
                                                @RequestParam(value = "djbh", required = false) String djbh,
                                                @RequestParam(value = "blzdbm", required = false) String blzdbm,
                                                @RequestParam(value = "blzmc", required = false) String blzmc,
                                                @RequestParam(value = "blztms", required = false) String blztms,
                                                @RequestParam(value = "byjssj", required = false) String byjssj,
                                                @RequestParam(value = "bykssj", required = false) String bykssj,
                                                @RequestParam(value = "hqhwmc", required = false) String hqhwmc,
                                                @RequestParam(value = "pmmc", required = false) String pmmc,
                                                @RequestParam(value = "startpj", required = false) String startpj,
                                                @RequestParam(value = "yx", required = false) String yx,
                                                @RequestParam(value = "edit", required = false) String edit) {

        return service.setPj(fzmc,dzmc,djbh,blzdbm,blzmc,blztms,byjssj,bykssj,hqhwmc,pmmc,startpj,yx,edit);

    }
}
