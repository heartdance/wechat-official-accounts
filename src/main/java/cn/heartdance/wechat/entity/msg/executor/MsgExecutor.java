package cn.heartdance.wechat.entity.msg.executor;

import java.util.Map;

public interface MsgExecutor {
    String execute(Map<String,String> requestMap);
}
