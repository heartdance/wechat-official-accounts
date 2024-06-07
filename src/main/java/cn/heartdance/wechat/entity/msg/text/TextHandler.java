package cn.heartdance.wechat.entity.msg.text;

import cn.heartdance.wechat.util.wechat.WeChatConstant;

import java.util.Map;

public abstract class TextHandler {

    protected TextHandler next;
    private String[] keywords;

    public TextHandler(String... keywords) {
        this.keywords = keywords;
    }

    public void setNext(TextHandler next) {
        this.next = next;
    }

    public String sendMsg(Map<String,String> requestMap) {
        for (String keyword : this.keywords) {
            if (requestMap.get(WeChatConstant.CONTENT).contains(keyword)) {
                return send(requestMap);
            }
        }
        return next != null ? next.sendMsg(requestMap) : "";
    }

    abstract protected String send(Map<String, String> requestMap);
}
