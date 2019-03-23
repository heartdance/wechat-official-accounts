package com.cherlshall.wechat.entity.msg;

import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 视频消息
 */
public class VideoMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是视频消息！");
    }
}
