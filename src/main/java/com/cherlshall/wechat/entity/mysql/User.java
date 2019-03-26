package com.cherlshall.wechat.entity.mysql;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String openId;
    private Double money;
    private Integer prizeTime;

    public User() {

    }

    public User(String openId, Double money, Integer prizeTime) {
        this.openId = openId;
        this.money = money;
        this.prizeTime = prizeTime;
    }
}
