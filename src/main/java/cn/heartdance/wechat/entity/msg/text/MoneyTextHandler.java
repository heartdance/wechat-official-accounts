package cn.heartdance.wechat.entity.msg.text;

import cn.heartdance.wechat.entity.po.User;
import cn.heartdance.wechat.mapper.UserMapper;
import cn.heartdance.wechat.util.wechat.SendUtil;
import cn.heartdance.wechat.util.wechat.WeChatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MoneyTextHandler extends TextHandler {

    @Autowired
    private UserMapper userMapper;

    public MoneyTextHandler() {
        super("金钱", "金币", "money");
    }

    @Override
    protected String send(Map<String, String> requestMap) {
        User user = userMapper.getUserByOpenId(requestMap.get(WeChatConstant.FROM_USER_NAME));
        return SendUtil.sendTextMsg(requestMap, "你剩余金钱：" + user.getMoney());
    }
}
