package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.entity.Article;
import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaiduTextChain extends AbstractTextChain {

    public BaiduTextChain() {
        super("汪", "百度", "baidu");
    }

    public BaiduTextChain(String... keywords) {
        super(keywords);
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
