package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.entity.msg.strategy.MsgStrategy;
import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 上报地理位置
 */
public class ReportLocationMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您上报了地理位置");
    }
}
