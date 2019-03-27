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
        User user = userMapper.getUserByOpenId(openId);
        // 判断用户是否存在 不存在则初始化此用户
        if (user == null) {
            User newUser = new User(openId, 10.0,
                    Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
            try {
                userMapper.addUser(newUser);
            } catch (Exception ignored) {
                user = userMapper.getUserByOpenId(openId);
            }
        }
        if (user == null) {
            return SendUtil.sendTextMsg(requestMap, "出错了>_<");
        }
        // 计算应得金钱
        Integer prizeTime = user.getPrizeTime();
        int nowTime = Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000));
        Integer intervalTime = nowTime - prizeTime;
        int intervalHours = intervalTime / 60 / 60;
        double moneyIncrement = intervalHours > 24 ? 24 : intervalHours;
        if (moneyIncrement > 0) {
            // 更新金钱
            if (userMapper.updateUserMoneyAndPrizeTime(openId, moneyIncrement, nowTime, prizeTime) > 0) {
                double newMoney = user.getMoney() + moneyIncrement;
                return SendUtil.sendTextMsg(requestMap, "获得 " + moneyIncrement + " 金钱" +
                        "\n\n你的金钱余额为：" + newMoney);
            } else {
                return SendUtil.sendTextMsg(requestMap, "出错了>_<");
            }
        } else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return SendUtil.sendTextMsg(requestMap, "你才刚刚领取过奖励" +
                    "\n\n上次领取奖励时间：\n" + format.format(new Date(prizeTime * 1000L)));
        }
    }
}
