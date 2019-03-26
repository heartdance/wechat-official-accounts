package com.cherlshall.wechat.entity.msg.chain;

import com.alibaba.fastjson.JSONObject;
import com.cherlshall.wechat.config.TuringConfig;
import com.cherlshall.wechat.entity.Turing;
import com.cherlshall.wechat.util.HttpClient;
import com.cherlshall.wechat.util.wechat.SendUtil;
import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.Map;

public class TuringTextChain extends AbstractTextChain {

    public TuringTextChain() {
        super("");
    }

    public TuringTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        Turing turing = new Turing(TuringConfig.KEY, requestMap.get(WeChatConstant.CONTENT), requestMap.get(WeChatConstant.FROM_USER_NAME));
        String response = HttpClient.sendPostRequest(TuringConfig.URL, turing.toJson());
        String text = JSONObject.parseObject(response).getString("text");
        if (text.contains("大锤"))
            text += "\n[恭喜发财，大吉大利]\n80元";
        if (text.contains("小锤"))
            text += "\n[恭喜发财，大吉大利]\n40元";
        return SendUtil.sendTextMsg(requestMap, text);
    }
}
