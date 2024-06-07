package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 取消关注
 */
public class UnsubscribeMsgExecutor implements MsgExecutor {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "谢谢您的取关！");
    }
}
