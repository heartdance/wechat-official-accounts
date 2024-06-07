package cn.heartdance.wechat.entity;

import com.alibaba.fastjson2.JSONObject;

public class Turing {
    // 图灵key
    private final String key;
    // 发送给图灵的信息
    private final String info;
    // 用户id 图灵根据此id确定是否为同一人 提供更连贯的回复
    private final String userid;

    public Turing(String key, String info, String userid) {
        this.key = key;
        this.info = info;
        this.userid = userid;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", this.key);
        jsonObject.put("info", this.info);
        jsonObject.put("userid", this.userid);
        return jsonObject.toJSONString();
    }
}
