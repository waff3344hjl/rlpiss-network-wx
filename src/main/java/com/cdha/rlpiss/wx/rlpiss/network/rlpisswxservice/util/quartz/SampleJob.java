package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.quartz;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.wxpush_service.WxPushMsgService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 首先定义一个 Job 需要继承 QuartzJobBean，示例中 Job 定义一个变量 Name，用于在定时执行的时候传入。
 */
public class SampleJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    WxPushMsgService service;


    private String name;


    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
//        doPushByOrderTime();
//        doPushByOrderTime2();
        System.out.println(String.format("Hello %s!" + new Date(), this.name));
    }

    private void doPushByOrderTime() {
        try {
            service.pushXQDshouli();//需求单受理
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        try {

            service.pusxZxwcXx();//装卸完成

        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        try {

            service.pushYySlToHz();//预约推送至货主

        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        try {

            service.pushYySlToSj();//预约推送至司机

        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        try {

            service.pushJfwcXx();//装卸完成

        } catch (Exception e) {
            logger.error(e.getMessage());

        }

        try {

            service.pushQcJcmXx();//进出门完成
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
    }



    private void doPushByOrderTime2() {
        try {
            service.pushXQDshouli();//需求单受理
            service.pusxZxwcXx();//装卸完成
            service.pushYySlToHz();//预约推送至货主
            service.pushYySlToSj();//预约推送至司机
            service.pushJfwcXx();//装卸完成
            service.pushQcJcmXx();//进出门完成
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException("=== 推送模块异常 ===="+e.getMessage());

        }
    }
}