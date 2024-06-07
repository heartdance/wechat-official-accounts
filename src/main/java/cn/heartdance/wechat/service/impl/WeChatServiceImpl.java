package cn.heartdance.wechat.service.impl;

import cn.heartdance.wechat.entity.msg.executor.MsgExecutorSelector;
import cn.heartdance.wechat.entity.msg.executor.MsgExecutor;
import cn.heartdance.wechat.entity.msg.executor.MsgExecutorContext;
import cn.heartdance.wechat.util.wechat.XmlUtil;
import cn.heartdance.wechat.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private MsgExecutorSelector msgExecutorSelector;

    public String processRequest(HttpServletRequest request) {
        // 默认返回的文本消息内容
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = XmlUtil.xmlToMap(request);
            // 工厂类生产策略
            MsgExecutor msgExecutor = msgExecutorSelector.selectMsgExecutor(requestMap);
            // 将策略交给执行策略的上下文
            MsgExecutorContext context = new MsgExecutorContext(msgExecutor);
            return context.execute(requestMap);
        } catch (Exception e) {
            log.error("processRequest error", e);
            return "";
        }
    }
}
