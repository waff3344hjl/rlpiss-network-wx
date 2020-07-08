package com.cdha.rlpiss.wx.wxservice.wxservice.config;

import com.alibaba.fastjson.JSON;
import com.cdha.rlpiss.wx.wxservice.wxservice.hander.gm.GmManagerException;
import com.cdha.wechatsub.wxtools.modle.MyOrder;
import com.cdha.wechatsub.wxtools.modle.TestUserBean;
import com.cdha.wechatsub.wxtools.util.MyBase64Util.MyBase64Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class RestTemplateManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public TestUserBean client(String url, Map<String, String> params) {

        RestTemplate template = new RestTemplate();

        return template.getForEntity(url, TestUserBean.class, params).getBody();
    }

    public String clientByPost(String url, String person) {

        RestTemplate template = new RestTemplate();

        return template.postForEntity(url, person, String.class).getBody();
    }

    public MyOrder clientByPostFromOrder(String url, MyOrder person) {

        RestTemplate template = new RestTemplate();

        return template.postForEntity(url, person, MyOrder.class).getBody();
    }

    public String clientByPostFromObject(String url, HttpEntity<MultiValueMap<String, String>> mapHttpEntity) {

        RestTemplate template = new RestTemplate();

        return template.postForEntity(url, mapHttpEntity, String.class).getBody();
    }

//    public static void main(String args[]) {
//        System.out.println( MyBase64Helper.SetGzip("http://cdhawxgzhx.easy.echosite.cn/ScjWebservice/Hyz_GetDate"));
//    }


    public String getResponseByAQPT(String lj, String url, String data, String reqUrl) {

        try {
            HttpHeaders headers = new HttpHeaders();
//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
            headers.add("Content-Type", "application/x-www-form-urlencoded");
//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//参数加密
            String urlZip = MyBase64Helper.SetGzip(url);//方法名加密
            String dataZip = MyBase64Helper.SetGzip(data);//参数加密
//  也支持中文
            params.add("lj", lj);//自定义参数
            params.add("url", urlZip);//转发url
            params.add("data", dataZip);//具体请求体


            //请求地址解密
            String rqUrl2 = MyBase64Helper.GetGzip(reqUrl);
//            logger.info("请求地址:"+rqUrl2);


            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

            String xml =
                    this.clientByPostFromObject(rqUrl2, requestEntity);
            //针对内网服务返回，去除XML中 string 头
            if (xml.contains("<?xml version=\"1.0\" encoding=\"utf-8\"?>")) {
                xml = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
            }
            if (xml.contains("<string xmlns=\"http://tempuri.org/\">")) {
                xml = xml.replace("<string xmlns=\"http://tempuri.org/\">", "");
            }
            if (xml.contains("</string>")) {
                xml = xml.replace("</string>", "");
            }
            logger.info("AQPT___方法___" + url + "===||===" + xml);
//            log.info("AQPT___方法___" + url + "===||===" + xml);
            String r = MyBase64Helper.GetGzip(xml);
            logger.info("AQPT___方法___" + url + "===||===" + r);
//            log.info("AQPT___方法___" + url + "===||===" + r);
            return r;
        } catch (Exception e) {
            throw new GmManagerException("方法:" + url + "_" + "访问安全平台失败:===="+e.getMessage());
        }

    }

    public String clientByPostJsonObject(String url, HttpEntity<String> jsonHttpEntity) {

        RestTemplate template = new RestTemplate();

        return template.postForEntity(url, jsonHttpEntity, String.class).getBody();
    }

    public  <T> T  getTokenByDS(String url, JSONObject json, Class<T> clazz ) {
//        com.alibaba.fastjson.JSONObject jsonObject  = com.alibaba.fastjson.JSONObject.toJSON()

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);
        String s = this.clientByPostJsonObject(url, formEntity);
        logger.info("访问多少联运："+s);
        return JSON.parseObject(s,clazz);
    }
    public String getRlpissDataString(String url, String json) {

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        //构造加密请求头
        headers.add("token","123");
        headers.add("timestamp","2020-03-26 16:47:15");
        headers.add("sign","123456789");

        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
        String s = this.clientByPostJsonObject(url, formEntity);
        logger.info("外网服务 --访问多式联运：" + s);
//        return JSON.parseObject(s,clazz);
        return s;
    }
}
