package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.entity.mysql.User;
import com.cherlshall.wechat.mapper.UserMapper;
import com.cherlshall.wechat.util.sql.MapperUtil;
import com.cherlshall.wechat.util.wechat.SendUtil;
import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PriceTextChain extends AbstractTextChain {

    public PriceTextChain() {
    }

    @Override
    public String sendMsg(Map<String,String> requestMap) {
        if ("领取奖励".equals(requestMap.get(WeChatConstant.CONTENT)) ||
                "领奖".equals(requestMap.get(WeChatConstant.CONTENT))) {
            return send(requestMap);
        }
        return next != null ? next.sendMsg(requestMap) : new TuringTextChain().send(requestMap);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        UserMapper userMapper = mapperUtil.getUserMapper();
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        if (userMapper.getUserCountByOpenId(openId) < 1) {
            User user = new User(openId, 10.0,
                    Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
            try {
                userMapper.addUser(user);
            } catch (Exception ignored) {
            }
        }
        synchronized (PriceTextChain.class) {
            User user = userMapper.getUserByOpenId(openId);
            double money = user.getMoney();
            Integer prizeTime = user.getPrizeTime();
            int nowTime = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));
            Integer intervalTime = nowTime - prizeTime;
            int intervalHours = intervalTime / 60 / 60;
            double moneyIncrement = intervalHours > 24 ? 24 : intervalHours;
            double newMoney = money + moneyIncrement;
            if (moneyIncrement > 0) {
                userMapper.updateUserMoneyAndPrizeTime(openId, newMoney, nowTime);
                return SendUtil.sendTextMsg(requestMap, "获得" + moneyIncrement + "金钱" +
                        "\n你的金钱剩余：" + newMoney);
            } else {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return SendUtil.sendTextMsg(requestMap, "你才刚刚领取过奖励" +
                        "\n\n上次领取奖励时间：" + format.format(new Date(prizeTime * 1000L)) +
                        "\n你的金钱剩余：" + newMoney);
            }
        }
    }
}
