package com.cherlshall.wechat.entity.msg;

import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 关注
 */
public class SubscribeMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "谢谢您的关注！");
    }
}
