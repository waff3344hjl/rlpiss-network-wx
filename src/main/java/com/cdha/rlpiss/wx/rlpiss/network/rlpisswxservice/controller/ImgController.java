package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseDSLYRqData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.wechatsub.wxtools.common.BaiDuAIConfig;
import com.cdha.wechatsub.wxtools.modle.BaiDuFormInf;
import com.cdha.wechatsub.wxtools.util.MyBase64Util.MyBase64Helper;
import com.cdha.wechatsub.wxtools.util.baiduai.BaiDuAIUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private BaiDuAIConfig aiConfig;

    private Logger logger = LoggerFactory.getLogger(getClass());
    //springboot策略
    @RequestMapping(value = "/get_image")
    @CrossOrigin
    public BaseResponseData<List<String>> getImage(@RequestParam(value = "UID", required = false) String UID) throws IOException {
        logger.info("get_image3___"+"UID"+":"+UID);

        String    path = "D:/test/2019\\7\\29\\a93127fc5e0a44c18f5ea6b4f5b99de1.jpg";
        String    path1 = "D:/test/2019\\7\\29\\61302726bbe348ff9032d39dba7b39f7.jpg";
//        File file = new File(path);
//        FileInputStream inputStream = new FileInputStream(file);
//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes, 0, inputStream.available());
        BaseResponseData<List<String>> myData =  new BaseResponseData<>();
        List<String> imgs = new ArrayList<>();
try {
    myData.setCode("0");
    imgs.add(MyBase64Helper.ImageToBase64(path));
    imgs.add(MyBase64Helper.ImageToBase64(path1));
    myData.setRsData(imgs);
    return  myData;
}catch (Exception e){
    myData.setCode("-1");
    return myData;
}

    }


    //springboot策略
    @RequestMapping(value = "/get_image3", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(
                           HttpServletResponse response) throws IOException {
        String    path = "D:/test/2019\\7\\29\\a93127fc5e0a44c18f5ea6b4f5b99de1.jpg";
        logger.info("get_image3___"+"IMGPATH"+":"+path);

//        String path = "D:/201911120557480101/0001.jpg";
//        String  path = "D:/test/2019\\6\\26\\364c71e664694d4d82c074d5317ee0e6.png";

//        String path = "D:/test/2019\\6\\26\\364c71e664694d4d82c074d5317ee0e6.png";
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    //springboot策略
    @RequestMapping(value = "/baidu", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public void getImage11() throws IOException {
        BaiDuAIUtil aiUtil = new BaiDuAIUtil();
//        WenZiShiBieInfo info = aiUtil.getBaiDuAiWz(aiConfig.getAppID(), aiConfig.getAPIKey(), aiConfig.getSecretKey());
//        List<String> rs = new ArrayList<>();
//        List<WenZiShiBieInfo.Words_result> ll = info.getWords_result();
//        for (WenZiShiBieInfo.Words_result data:
//        ll) {
//            rs.add(data.getWords());
//            System.out.println(data.getWords());
//        }
        BaiDuFormInf info = aiUtil.getBaiDuAiFrom(aiConfig.getAppID(), aiConfig.getAPIKey(), aiConfig.getSecretKey());

        logger.error(info.toString());
    }


    /**
     * @param rqData 1
     * @return 1
     */
    @CrossOrigin
    @RequestMapping(path = "tttt")
    public JSONObject tttt(@RequestBody BaseDSLYRqData<String> rqData) {

        log.info("====   测试方法进入   ===="+rqData.toString());

        BaseDSLYReData<String> data = new BaseDSLYReData<>();
        data.setMsg("0");
        data.setReturnCode("0");
        data.setData("0");

        return JSONObject.parseObject(JSON.toJSONString(data));
    }

}
