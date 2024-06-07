package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 视频消息
 */
public class VideoMsgExecutor implements MsgExecutor {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是视频消息！");
    }
}
