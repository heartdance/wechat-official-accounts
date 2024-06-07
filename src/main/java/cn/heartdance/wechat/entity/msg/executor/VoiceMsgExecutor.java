package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 语音消息
 */
public class VoiceMsgExecutor implements MsgExecutor {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是语音消息！");
    }
}
