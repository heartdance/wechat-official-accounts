package cn.heartdance.wechat.entity.msg.executor;

import java.util.Map;

public class MsgExecutorContext {

    private final MsgExecutor msgExecutor;

    public MsgExecutorContext(MsgExecutor msgExecutor) {
        this.msgExecutor = msgExecutor;
    }

    public String execute(Map<String,String> requestMap) {
        // before
        // TODO
        // execute
        String res = this.msgExecutor.execute(requestMap);
        // after
        // TODO
        return res;
    }
}
