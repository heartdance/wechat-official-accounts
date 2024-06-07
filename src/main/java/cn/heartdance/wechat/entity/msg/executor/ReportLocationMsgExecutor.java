package cn.heartdance.wechat.entity.msg.executor;

import cn.heartdance.wechat.util.wechat.SendUtil;

import java.util.Map;

/**
 * 事件消息 - 上报地理位置
 */
public class ReportLocationMsgExecutor implements MsgExecutor {
    @Override
    public String execute(Map<String,String> requestMap) {
        return SendUtil.sendTextMsg(requestMap, "您上报了地理位置");
    }
}
