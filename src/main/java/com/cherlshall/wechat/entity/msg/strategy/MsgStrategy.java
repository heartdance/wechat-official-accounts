package com.cherlshall.wechat.entity.msg.strategy;

import java.util.Map;

public interface MsgStrategy {
    String execute(Map<String,String> requestMap);
}
