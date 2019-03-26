package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.entity.Article;
import com.cherlshall.wechat.util.wechat.SendUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TencentTextChain extends AbstractTextChain {

    public TencentTextChain() {
        super("喵", "腾讯", "tencent");
    }

    public TencentTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        List<Article> items = new ArrayList<>();
        Article article = new Article();
        article.setTitle("腾讯");
        article.setDescription("用心创造快乐，没钱WNMB");
        article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553281585670&di=1c48c5744f32fb73f46b95b5b1e3f5d9&imgtype=0&src=http%3A%2F%2Fimg.eeyy.com%2Fuploadfile%2F2015%2F0526%2F20150526035704342.jpg");
        article.setUrl("http://www.qq.com");
        items.add(article);
        return SendUtil.sendArticleMsg(requestMap, items);
    }
}
