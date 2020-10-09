package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push;

import cn.jpush.api.push.model.PushPayload;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushBean;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushMsgBean;
import org.springframework.stereotype.Repository;

/**
 * 推送服务
 * 封装业务功能相关
 */
@Repository
public interface MyJiGuangPushService {
    boolean pushAll(PushBean pushBean);

    boolean pushIos(PushBean pushBean);

    boolean pushIos(PushBean pushBean, String... registids);

    boolean pushAndroid(PushBean pushBean);

    /**
     * 自定义消息推送+全体
     * @param pushMsgBean 自定义消息
     * @return 1
     */
    boolean pushAndroid(PushMsgBean pushMsgBean);

    /**
     *  通知+自定义消息 +全体
     * @param pushBean 通知
     * @param pushMsgBean 自定义消息
     * @return 1
     */
    boolean pushAndroid(PushBean pushBean, PushMsgBean pushMsgBean);

    boolean pushAndroid(PushBean pushBean, String... registids);

    /**
     * 别名推送+通知
     * @param pushBean 1
     * @param alias1 1
     * @return 1
     */
    boolean pushAndroidByalias(PushBean pushBean, String alias1);

    /**
     * 别名推送+自定义消息
     * @param pushMsgBean 自定义消息
     * @param alias1 别名
     * @return 1
     */
    boolean pushAndroidByalias(PushMsgBean pushMsgBean, String alias1);

    /**
     * 别名推送+通知+自定义消息
     * @param pushBean 通知
     * @param pushMsgBean 消息
     * @param alias1 别名
     * @return 1
     */
    boolean pushAndroidByalias(PushBean pushBean, PushMsgBean pushMsgBean, String alias1);

    /**
     * 标签推送+通知
     * @param pushBean 1
     * @param tag 标签
     * @return 1
     */
    boolean pushAndroidByTag(PushBean pushBean, String tag);

    /**
     * 标签推送+自定义消息
     * @param pushMsgBean 自定义消息
     * @param tag  标签
     * @return 1
     */
    boolean pushAndroidByTag(PushMsgBean pushMsgBean, String tag);

    /**
     * 标签推送 +通知+消息
     * @param pushBean 通知
     * @param pushMsgBean 消息
     * @param tag  标签
     * @return 1
     */
    boolean pushAndroidByTag(PushBean pushBean, PushMsgBean pushMsgBean, String tag);
    boolean sendPush(PushPayload pushPayload);



}
