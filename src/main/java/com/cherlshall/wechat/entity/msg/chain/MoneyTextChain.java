package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.entity.mysql.User;
import com.cherlshall.wechat.mapper.UserMapper;
import com.cherlshall.wechat.util.sql.MapperUtil;
import com.cherlshall.wechat.util.wechat.SendUtil;
import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.Map;

public class MoneyTextChain extends AbstractTextChain {

    public MoneyTextChain() {
        super("金钱", "金币", "money");
    }

    public MoneyTextChain(String... keywords) {
        super(keywords);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        UserMapper userMapper = mapperUtil.getUserMapper();
        User user = userMapper.getUserByOpenId(requestMap.get(WeChatConstant.FROM_USER_NAME));
        return SendUtil.sendTextMsg(requestMap, "你剩余金钱：" + user.getMoney());
    }
}
