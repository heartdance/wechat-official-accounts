package com.cherlshall.wechat.entity.msg;

import com.alibaba.fastjson.JSONObject;
import com.cherlshall.wechat.config.TulingConfig;
import com.cherlshall.wechat.entity.Article;
import com.cherlshall.wechat.entity.Tuling;
import com.cherlshall.wechat.util.HttpClient;
import com.cherlshall.wechat.util.wechat.SendUtil;
import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文字消息
 */
public class TextMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        String content = requestMap.get(WeChatConstant.CONTENT);
        if (content != null && content.contains("喵")) {
            List<Article> items = new ArrayList<>();
            Article article = new Article();
            article.setTitle("腾讯");
            article.setDescription("用心创造快乐，没钱WNMB");
            article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553281585670&di=1c48c5744f32fb73f46b95b5b1e3f5d9&imgtype=0&src=http%3A%2F%2Fimg.eeyy.com%2Fuploadfile%2F2015%2F0526%2F20150526035704342.jpg");
            article.setUrl("http://www.qq.com");
            items.add(article);
            return SendUtil.sendArticleMsg(requestMap, items);
        } else {
            Tuling tuling = new Tuling(TulingConfig.KEY, content, requestMap.get(WeChatConstant.FROM_USER_NAME));
            String response = HttpClient.sendPostRequest(TulingConfig.URL, tuling.toJson());
            String text = JSONObject.parseObject(response).getString("text");
            if (text.contains("大锤"))
                text += "\n[恭喜发财，大吉大利]\n80元";
            if (text.contains("小锤"))
                text += "\n[恭喜发财，大吉大利]\n40元";
            return SendUtil.sendTextMsg(requestMap, text);
        }
    }
}
