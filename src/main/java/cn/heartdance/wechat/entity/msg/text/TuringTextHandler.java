package cn.heartdance.wechat.entity.msg.text;

import com.alibaba.fastjson2.JSONObject;
import cn.heartdance.wechat.config.TuringConfig;
import cn.heartdance.wechat.entity.Turing;
import cn.heartdance.wechat.util.HttpClient;
import cn.heartdance.wechat.util.wechat.SendUtil;
import cn.heartdance.wechat.util.wechat.WeChatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TuringTextHandler extends TextHandler {

    @Autowired
    private TuringConfig turingConfig;

    public TuringTextHandler() {
        super("");
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        Turing turing = new Turing(turingConfig.getKey(), requestMap.get(WeChatConstant.CONTENT), requestMap.get(WeChatConstant.FROM_USER_NAME));
        String response = HttpClient.sendPostRequest(turingConfig.getUrl(), turing.toJson());
        String text = JSONObject.parseObject(response).getString("text");
        if (text.contains("大锤"))
            text += "\n[恭喜发财，大吉大利]\n80元";
        if (text.contains("小锤"))
            text += "\n[恭喜发财，大吉大利]\n40元";
        return SendUtil.sendTextMsg(requestMap, text);
    }
}
