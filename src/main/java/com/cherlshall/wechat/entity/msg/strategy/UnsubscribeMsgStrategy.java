package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.entity.msg.strategy.MsgStrategy;
import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 取消关注
 */
public class UnsubscribeMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "谢谢您的取关！");
    }
}
