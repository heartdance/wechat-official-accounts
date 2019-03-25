package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.entity.msg.strategy.MsgStrategy;
import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 语音消息
 */
public class VoiceMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是语音消息！");
    }
}
