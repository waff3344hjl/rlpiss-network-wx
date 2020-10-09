package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push;


import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushBean;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean.PushMsgBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JiGuangPushServiceImpl  implements JiGuangPushService {
    /** 一次推送最大数量 (极光限制1000) */
    private static final int max_size = 800;
//    @Qualifier("myJiGuangPushService")
    @Autowired
    private MyJiGuangPushServiceImpl jPushService;
    /**
     * 推送全部, 不支持附加信息
     * @return 1
     */
    @Override
    public boolean pushAll(PushBean pushBean){
        return jPushService.pushAll(pushBean);
    }
    /**
     * 推送全部ios
     * @return 1
     */
    @Override
    public boolean pushIos(PushBean pushBean){
        return jPushService.pushIos(pushBean);
    }
    /**
     * 推送ios 指定id
     * @return 1
     */
    @Override
    public boolean pushIos(PushBean pushBean, String... registids){
        registids = checkRegistids(registids); // 剔除无效registed
        while (registids.length > max_size) { // 每次推送max_size个
            jPushService.pushIos(pushBean, Arrays.copyOfRange(registids, 0, max_size));
            registids = Arrays.copyOfRange(registids, max_size, registids.length);
        }
        return jPushService.pushIos(pushBean, registids);
    }
    /**
     * 推送全部android
     * @return 1
     */
    @Override
    public boolean pushAndroid(PushBean pushBean){
        return jPushService.pushAndroid(pushBean);
    }

    @Override
    public boolean pushAndroid(PushMsgBean pushMsgBean) {
        return jPushService.pushAndroid( pushMsgBean) ;
    }

    @Override
    public boolean pushAndroid(PushBean pushBean, PushMsgBean pushMsgBean) {
        return jPushService.pushAndroid( pushBean,  pushMsgBean);
    }

    /**
     * 推送android 指定id
     * @return 1
     */
    @Override
    public boolean pushAndroid(PushBean pushBean, String... registids){
        registids = checkRegistids(registids); // 剔除无效registed
        while (registids.length > max_size) { // 每次推送max_size个
            jPushService.pushAndroid(pushBean, Arrays.copyOfRange(registids, 0, max_size));
            registids = Arrays.copyOfRange(registids, max_size, registids.length);
        }
        return jPushService.pushAndroid(pushBean, registids);
    }
    @Transactional
    @Override
    public boolean pushAndroidByalias(PushBean pushBean, String alias1) {
        return jPushService.pushAndroidByalias( pushBean,  alias1);
    }

    @Override
    public boolean pushAndroidByalias(PushMsgBean pushMsgBean, String alias1) {
        return jPushService. pushAndroidByalias( pushMsgBean,  alias1);
    }

    @Override
    public boolean pushAndroidByalias(PushBean pushBean, PushMsgBean pushMsgBean, String alias1) {
        return jPushService.pushAndroidByalias( pushBean,  pushMsgBean,  alias1);
    }

    @Override
    public boolean pushAndroidByTag(PushBean pushBean, String tag) {
        return jPushService. pushAndroidByTag( pushBean,  tag);
    }

    @Override
    public boolean pushAndroidByTag(PushMsgBean pushMsgBean, String tag) {
        return jPushService.pushAndroidByTag( pushMsgBean,  tag);
    }

    @Override
    public boolean pushAndroidByTag(PushBean pushBean, PushMsgBean pushMsgBean, String tag) {
        return jPushService.pushAndroidByTag( pushBean,  pushMsgBean,  tag);
    }

    /**
     * 剔除无效registed
     * @param registids 1
     * @return 1
     */
    @Override
    public String[] checkRegistids(String[] registids) {
        List<String> regList = new ArrayList<String>(registids.length);
        for (String registid : registids) {
            if (registid!=null && !"".equals(registid.trim())) {
                regList.add(registid);
            }
        }
        return regList.toArray(new String[0]);
    }

}
