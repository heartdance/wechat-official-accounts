package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.entity.po.User;
import cn.heartdance.wechat.mapper.UserMapper;
import cn.heartdance.wechat.util.wechat.SendUtil;
import cn.heartdance.wechat.util.wechat.WeChatConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 事件消息 - 关注
 */
@Component
public class SubscribeMsgExecutor implements MsgExecutor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String execute(Map<String,String> requestMap) {
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
