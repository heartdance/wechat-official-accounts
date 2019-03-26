package com.cherlshall.wechat.entity.msg.chain;

import com.cherlshall.wechat.entity.mysql.Npc;
import com.cherlshall.wechat.entity.mysql.User;
import com.cherlshall.wechat.mapper.NpcMapper;
import com.cherlshall.wechat.mapper.UserMapper;
import com.cherlshall.wechat.util.sql.MapperUtil;
import com.cherlshall.wechat.util.wechat.SendUtil;
import com.cherlshall.wechat.util.wechat.WeChatConstant;

import java.util.Map;

public class HammerOrMilkTextChain extends AbstractTextChain {

    private int hpIncrement = 0;
    private double moneyDecrement = 0;

    public HammerOrMilkTextChain() {
    }

    @Override
    public String sendMsg(Map<String,String> requestMap) {
        if ("使用大锤".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = -3;
            this.moneyDecrement = 4;
            return send(requestMap);
        } else if ("使用小锤".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = -1;
            this.moneyDecrement = 2;
            return send(requestMap);
        } else if ("使用奶粉".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = -8;
            this.moneyDecrement = 8;
            return send(requestMap);
        } else if ("使用奶瓶".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = 4;
            this.moneyDecrement = 5;
            return send(requestMap);
        }
        return next != null ? next.sendMsg(requestMap) : new TuringTextChain().send(requestMap);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        MapperUtil mapperUtil = MapperUtil.getInstance();
        NpcMapper npcMapper = mapperUtil.getNpcMapper();
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
        synchronized (HammerOrMilkTextChain.class) {
            User user = userMapper.getUserByOpenId(openId);
            double money = user.getMoney();
            if (money > this.moneyDecrement) {
                Npc npc = npcMapper.getNpcById(1);
                int newHp = npc.getHp() + hpIncrement;
                double newMoney = money - moneyDecrement;
                npcMapper.updateNpcHp(1, newHp);
                userMapper.updateUserMoney(openId, newMoney);
                switch (this.hpIncrement) {
                    case -3: return SendUtil.sendTextMsg(requestMap, "你拎着大锤走向周建云...\n\n周建云剩余血量：" + newHp +
                            "\n你的剩余金钱：" + newMoney);
                    case -1: return SendUtil.sendTextMsg(requestMap, "小锤直接被周建云吃了，他捂着肚子一脸生无可恋...\n\n周建云剩余血量：" + newHp +
                            "\n你的剩余金钱：" + newMoney);
                    case -8: return SendUtil.sendTextMsg(requestMap, "你喂周建云喝下去一口毒奶...\n\n周建云剩余血量：" + newHp +
                            "\n你的剩余金钱：" + newMoney);
                    case 4: return SendUtil.sendTextMsg(requestMap, "你把奶瓶递给周建云，只见他爱不释口...\n\n周建云剩余血量：" + newHp +
                            "\n你的剩余金钱：" + newMoney);
                    default: return SendUtil.sendTextMsg(requestMap, "出错了...\n\n周建云剩余血量：" + newHp +
                            "\n你的剩余金钱：" + newMoney);
                }
            } else {
                return SendUtil.sendTextMsg(requestMap, "金钱不足，你仅剩" + money + "金钱了");
            }
        }
    }
}
