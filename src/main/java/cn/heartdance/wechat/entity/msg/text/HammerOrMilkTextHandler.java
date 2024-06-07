package cn.heartdance.wechat.entity.msg.text;

import cn.heartdance.wechat.entity.po.User;
import cn.heartdance.wechat.mapper.NpcMapper;
import cn.heartdance.wechat.mapper.UserMapper;
import cn.heartdance.wechat.util.wechat.SendUtil;
import cn.heartdance.wechat.util.wechat.WeChatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HammerOrMilkTextHandler extends TextHandler {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NpcMapper npcMapper;

    private int hpIncrement = 0;
    private double moneyIncrement = 0;

    @Override
    public String sendMsg(Map<String,String> requestMap) {
        if ("使用大锤".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = -3;
            this.moneyIncrement = -4;
            return send(requestMap);
        } else if ("使用小锤".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = -1;
            this.moneyIncrement = -2;
            return send(requestMap);
        } else if ("使用奶粉".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = -8;
            this.moneyIncrement = -8;
            return send(requestMap);
        } else if ("使用奶瓶".equals(requestMap.get(WeChatConstant.CONTENT))) {
            this.hpIncrement = 4;
            this.moneyIncrement = -5;
            return send(requestMap);
        }
        return next != null ? next.sendMsg(requestMap) : new TuringTextHandler().send(requestMap);
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        String openId = requestMap.get(WeChatConstant.FROM_USER_NAME);
        if (userMapper.updateUserMoney(openId, this.moneyIncrement) > 0) {
            npcMapper.updateNpcHp(1, hpIncrement);
            switch (this.hpIncrement) {
                case -3: return SendUtil.sendTextMsg(requestMap, "你拎着大锤走向周建云...\n\n周建云血量 -3" +
                        "\n你的金钱 -4");
                case -1: return SendUtil.sendTextMsg(requestMap, "小锤直接被周建云吃了，他捂着肚子一脸生无可恋...\n\n周建云血量 -1" +
                        "\n你的金钱 -2");
                case -8: return SendUtil.sendTextMsg(requestMap, "你喂周建云喝下去一口毒奶...\n\n周建云血量 -8" +
                        "\n你的金钱 -8");
                case 4: return SendUtil.sendTextMsg(requestMap, "你把奶瓶递给周建云，只见他爱不释口...\n\n周建云血量 +4" +
                        "\n你的金钱 -5");
                default: return SendUtil.sendTextMsg(requestMap, "出错了>_<");
            }
        } else {
            User user = userMapper.getUserByOpenId(openId);
            return SendUtil.sendTextMsg(requestMap, "金钱不足，你仅剩" + user.getMoney() + "金钱了");
        }
    }
}
