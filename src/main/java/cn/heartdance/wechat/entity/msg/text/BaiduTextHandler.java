package cn.heartdance.wechat.entity.msg.text;

import cn.heartdance.wechat.entity.Article;
import cn.heartdance.wechat.util.wechat.SendUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaiduTextHandler extends TextHandler {

    public BaiduTextHandler() {
        super("汪", "百度", "baidu");
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        List<Article> items = new ArrayList<>();
        Article article = new Article();
        article.setTitle("百度");
        article.setDescription("百度一下，你就知道");
        article.setPicUrl("https://www.baidu.com/img/bd_logo1.png?where=super");
        article.setUrl("https://www.baidu.com");
        items.add(article);
        return SendUtil.sendArticleMsg(requestMap, items);
    }
}
