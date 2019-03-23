package com.cherlshall.wechat.entity.msg;

import java.util.Map;

public class MsgStrategyContext {

    private Map<String,String> requestMap;

    public MsgStrategyContext(Map<String,String> requestMap) {
        this.requestMap = requestMap;
    }

    public String execute() {
        MsgStrategy msgStrategy = MsgStrategyFactory.getReceiveMsg(this.requestMap);
        return msgStrategy.execute(this.requestMap);
    }
}
