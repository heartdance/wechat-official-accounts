package cn.heartdance.wechat.service;

import jakarta.servlet.http.HttpServletRequest;

public interface WeChatService {
    String processRequest(HttpServletRequest request);
}
