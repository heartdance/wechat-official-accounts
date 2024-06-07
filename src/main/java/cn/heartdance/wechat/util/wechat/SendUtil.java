package cn.heartdance.wechat.util.wechat;

import cn.heartdance.wechat.entity.Article;


import java.util.*;

public class SendUtil {

    /**
     * 回复文本消息
     */
    public static String sendTextMsg(Map<String,String> requestMap, String content){

        Map<String,Object> map = new HashMap<>();
        map.put("ToUserName", requestMap.get(WeChatConstant.FROM_USER_NAME));
        map.put("FromUserName", requestMap.get(WeChatConstant.TO_USER_NAME));
        map.put("MsgType", WeChatConstant.RESP_MESSAGE_TYPE_TEXT);
        map.put("CreateTime", new Date().getTime());
        map.put("Content", content);
        return XmlUtil.mapToXML(map);
    }

    /**
     * 回复图文消息
     */
    public static String sendArticleMsg(Map<String,String> requestMap, List<Article> articles){
        if(articles == null || articles.isEmpty()){
            return "";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("ToUserName", requestMap.get(WeChatConstant.FROM_USER_NAME));
        map.put("FromUserName", requestMap.get(WeChatConstant.TO_USER_NAME));
        map.put("MsgType", WeChatConstant.RESP_MESSAGE_TYPE_NEWS);
        map.put("CreateTime", new Date().getTime());
        List<Map<String, Object>> articleMaps = new ArrayList<>();
        for(Article article : articles){
            Map<String,Object> item = new HashMap<>();
            Map<String,Object> itemContent = new HashMap<>();
            itemContent.put("Title", article.getTitle());
            itemContent.put("Description", article.getDescription());
            itemContent.put("PicUrl", article.getPicUrl());
            itemContent.put("Url", article.getUrl());
            item.put("item",itemContent);
            articleMaps.add(item);
        }
        map.put("Articles", articleMaps);
        map.put("ArticleCount", articleMaps.size());
        return XmlUtil.mapToXML(map);
    }
}
