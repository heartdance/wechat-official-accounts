package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.entity.msg.chain.*;

import java.util.Map;

/**
 * 文字消息
 */
public class TextMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        // 拼装责任链
        AbstractTextChain priceChain = new PriceTextChain();
        AbstractTextChain hammerOrMilkChain = new HammerOrMilkTextChain();
        AbstractTextChain hpChain = new HpTextChain();
        MoneyTextChain moneyChain = new MoneyTextChain();
        AbstractTextChain tencentChain = new TencentTextChain();
        AbstractTextChain baiduChain = new BaiduTextChain();

        priceChain.setNext(hammerOrMilkChain);
        hammerOrMilkChain.setNext(hpChain);
        hpChain.setNext(moneyChain);
        moneyChain.setNext(tencentChain);
        tencentChain.setNext(baiduChain);
        // 交给责任链执行
        return priceChain.sendMsg(requestMap);
    }
}
