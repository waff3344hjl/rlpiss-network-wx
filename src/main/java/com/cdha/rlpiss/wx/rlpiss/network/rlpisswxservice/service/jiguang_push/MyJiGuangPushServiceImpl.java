package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.JiGuangConfig;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushBean;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushMsgBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyJiGuangPushServiceImpl implements MyJiGuangPushService {
    @Autowired
    private JiGuangConfig jPushConfig;

    /**
     * 广播 (所有平台，所有设备, 不支持附加信息)
     *
     * @param pushBean 推送内容
     * @return 1
     */
    @Override
    public boolean pushAll(PushBean pushBean) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(pushBean.getAlert()))
                .build());
    }

    /**
     * ios广播
     *
     * @param pushBean 推送内容
     * @return 1
     */
    @Override
    public boolean pushIos(PushBean pushBean) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
                .build());
    }

    /**
     * ios通过registid推送 (一次推送最多 1000 个)
     *
     * @param pushBean  推送内容
     * @param registids 推送id
     * @return 1
     */
    @Override
    public boolean pushIos(PushBean pushBean, String... registids) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registids))
                .setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
                .build());
    }

    /**
     * android广播
     *
     * @param pushBean 推送内容
     * @return 1
     */
    @Override
    public boolean pushAndroid(PushBean pushBean) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .build());
    }

    /**
     * 自定义消息推送+全体Android
     *
     * @param pushMsgBean 自定义消息
     * @return 1
     */
    @Override
    public boolean pushAndroid(PushMsgBean pushMsgBean) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder()
                        .setMsgContent(pushMsgBean.getMsgContent())
                        .setTitle(pushMsgBean.getTitle())
                        .addExtras(pushMsgBean.getExtrasBuilder())
                        .build())
                .build());
    }

    /**
     * 全体 android  通知+消息
     *
     * @param pushBean    通知
     * @param pushMsgBean 自定义消息
     * @return 1
     */
    @Override
    public boolean pushAndroid(PushBean pushBean, PushMsgBean pushMsgBean) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .setMessage(Message.newBuilder()
                        .setMsgContent(pushMsgBean.getMsgContent())
                        .setTitle(pushMsgBean.getTitle())
                        .build())
                .build());
    }


    /**
     * android通过registid推送 (一次推送最多 1000 个)
     *
     * @param pushBean  推送内容
     * @param registids 推送id
     * @return 1
     */
    @Override
    public boolean pushAndroid(PushBean pushBean, String... registids) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registids))
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .build());
    }

    /**
     * 别名推送
     *
     * @param pushBean 1
     * @param alias1   1
     * @return 1
     */
    @Override
    public boolean pushAndroidByalias(PushBean pushBean, String alias1) {
        log.info(pushBean.toString());
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias1))
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .build());
    }

    @Override
    public boolean pushAndroidByalias(PushMsgBean pushMsgBean, String alias1) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias1))
                .setMessage(Message.newBuilder()
                        .setMsgContent(pushMsgBean.getMsgContent())
                        .setTitle(pushMsgBean.getTitle())
                        .build())
                .build());
    }

    @Override
    public boolean pushAndroidByalias(PushBean pushBean, PushMsgBean pushMsgBean, String alias1) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias1))
                .setNotification(Notification.android(pushBean.getAlert(),
                        pushBean.getTitle(),
                        pushBean.getExtras()))
                .setMessage(Message.newBuilder()
                        .setMsgContent(pushMsgBean.getMsgContent())
                        .setTitle(pushMsgBean.getTitle())
                        .build())
                .build());
    }

    /**
     * 标签推送
     *
     * @param pushBean 1
     * @param tag      1
     * @return 1
     */
    @Override
    public boolean pushAndroidByTag(PushBean pushBean, String tag) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .build());
    }

    @Override
    public boolean pushAndroidByTag(PushMsgBean pushMsgBean, String tag) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setMessage(Message.newBuilder()
                        .setMsgContent(pushMsgBean.getMsgContent())
                        .setTitle(pushMsgBean.getTitle())
                        .build())
                .build());
    }

    @Override
    public boolean pushAndroidByTag(PushBean pushBean, PushMsgBean pushMsgBean, String tag) {
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .setMessage(Message.newBuilder()
                        .setMsgContent(pushMsgBean.getMsgContent())
                        .setTitle(pushMsgBean.getTitle())
                        .build())
                .build());
    }

    /**
     * 调用api推送
     *
     * @param pushPayload 推送实体
     * @return 1
     */
    @Override
    public boolean sendPush(PushPayload pushPayload) {
        log.info("发送极光推送请求: {}", pushPayload);
        PushResult result = null;
        try {
            result = jPushConfig.getJPushClient().sendPush(pushPayload);
        } catch (APIConnectionException e) {
            log.error("极光推送连接异常: ", e);
        } catch (APIRequestException e) {
            log.error("极光推送请求异常: ", e);
        }
        if (result != null && result.isResultOK()) {
            log.info("极光推送请求成功: {}", result);
            return true;
        } else {
            log.info("极光推送请求失败: {}", result);
            return false;
        }
    }


}
