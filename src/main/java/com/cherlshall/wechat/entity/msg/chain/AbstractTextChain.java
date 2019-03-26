package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.Map;

public abstract class AbstractTextChain {

    protected AbstractTextChain next;
    private String[] keywords;

    public AbstractTextChain(String... keywords) {
        this.keywords = keywords;
    }

    public void setNext(AbstractTextChain next) {
        this.next = next;
    }

    public String sendMsg(Map<String,String> requestMap) {
        for (String keyword : this.keywords) {
            if (requestMap.get(WeChatConstant.CONTENT).contains(keyword)) {
                return send(requestMap);
            }
        }
        // 交给下一个责任接收者处理 如果没有下一个 则交给图灵机器人处理
        return next != null ? next.sendMsg(requestMap) : new TuringTextChain().send(requestMap);
    }

    abstract protected String send(Map<String, String> requestMap);
}
