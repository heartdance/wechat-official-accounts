package com.cherlshall.wechat.entity.msg.strategy;

import com.cherlshall.wechat.entity.mysql.User;
import com.cherlshall.wechat.mapper.UserMapper;
import com.cherlshall.wechat.util.sql.MapperUtil;
import com.cherlshall.wechat.util.wechat.SendUtil;
import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.Map;

/**
 * 事件消息 - 关注
 */
public class SubscribeMsgStrategy implements MsgStrategy {
    @Override
    public String execute(Map<String,String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        UserMapper userMapper = mapperUtil.getUserMapper();
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        if (userMapper.getUserCountByOpenId(openId) < 1) {
            User user = new User(openId, 10.0,
                    Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
            try {
                userMapper.addUser(user);
            } catch (Exception e) {
                return SendUtil.sendTextMsg(requestMap, "回来了老弟？");
            }
        } else {
            return SendUtil.sendTextMsg(requestMap, "回来了老弟？");
        }
        return SendUtil.sendTextMsg(requestMap, "谢谢您的关注！获得10金钱！");
    }
}
