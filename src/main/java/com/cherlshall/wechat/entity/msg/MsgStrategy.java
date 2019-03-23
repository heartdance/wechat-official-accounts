package com.cherlshall.wechat.entity.msg;

import java.util.Map;

public interface MsgStrategy {
    String execute(Map<String,String> requestMap);
}
