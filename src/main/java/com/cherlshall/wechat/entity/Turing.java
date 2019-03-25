package com.cherlshall.wechat.entity;

import com.alibaba.fastjson.JSONObject;

public class Turing {
    // 图灵key
    private String key;
    // 发送给图灵的信息
    private String info;
    // 用户id 图灵根据此id确定是否为同一人 提供更连贯的回复
    private String userid;

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
