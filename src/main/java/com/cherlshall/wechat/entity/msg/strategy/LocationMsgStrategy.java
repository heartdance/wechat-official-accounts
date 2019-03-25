package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 地理位置消息
 */
public class LocationMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是地理位置消息！");
    }
}
