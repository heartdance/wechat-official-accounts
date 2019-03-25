package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 链接消息
 */
public class LinkMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是链接消息！");
    }
}
