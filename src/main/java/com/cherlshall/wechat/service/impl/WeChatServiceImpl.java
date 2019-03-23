package com.cherlshall.wechat.service.impl;

import com.cherlshall.wechat.entity.msg.MsgStrategyContext;
import com.cherlshall.wechat.service.WeChatService;
import com.cherlshall.wechat.util.wechat.XmlUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    public String processRequest(HttpServletRequest request) {
        // 默认返回的文本消息内容
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = XmlUtil.xmlToMap(request);
            MsgStrategyContext context = new MsgStrategyContext(requestMap);
            return context.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
