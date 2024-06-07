package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 图片消息
 */
public class ImageMsgExecutor implements MsgExecutor {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您发送的是图片消息！");
    }
}
