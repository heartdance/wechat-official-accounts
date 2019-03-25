package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.entity.msg.strategy.MsgStrategy;

import java.util.Map;

public class MsgStrategyContext {

    private MsgStrategy msgStrategy;

    public MsgStrategyContext(MsgStrategy msgStrategy) {
        this.msgStrategy = msgStrategy;
    }

    public String execute(Map<String,String> requestMap) {
        // before
        // TODO
        // execute
        String execute = this.msgStrategy.execute(requestMap);
        // after
        // TODO
        return execute;
    }
}
