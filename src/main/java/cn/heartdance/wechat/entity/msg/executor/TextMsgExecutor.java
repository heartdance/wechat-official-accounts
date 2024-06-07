package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.entity.msg.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文字消息
 */
@Component
public class TextMsgExecutor implements MsgExecutor {

    @Autowired
    private PriceTextHandler priceTextHandler;

    @Autowired
    private HammerOrMilkTextHandler hammerOrMilkTextHandler;

    @Autowired
    private HpTextHandler hpTextHandler;

    @Autowired
    private MoneyTextHandler moneyTextHandler;

    @Autowired
    private TuringTextHandler turingTextHandler;

    @Override
    public String execute(Map<String,String> requestMap) {
        // 拼装责任链
        TextHandler tencentTextHandler = new TencentTextHandler();
        TextHandler baiduTextHandler = new BaiduTextHandler();

        priceTextHandler.setNext(hammerOrMilkTextHandler);
        hammerOrMilkTextHandler.setNext(hpTextHandler);
        hpTextHandler.setNext(moneyTextHandler);
        moneyTextHandler.setNext(tencentTextHandler);
        tencentTextHandler.setNext(baiduTextHandler);
        baiduTextHandler.setNext(turingTextHandler);
        // 交给责任链执行
        return priceTextHandler.sendMsg(requestMap);
    }
}
