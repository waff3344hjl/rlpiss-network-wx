package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.jiguang_push.bean;

import lombok.Data;

import java.util.Map;
@Data
public class PushMsgBean {
    private String title;
    private String msgContent;
    private String contentType;
    private Map<String, String> extrasBuilder;
    private Map<String, Number> numberExtrasBuilder;
    private Map<String, Boolean> booleanExtrasBuilder;
}
