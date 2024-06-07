package cn.heartdance.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 认证信息 前往微信公众号管理页面获取
 */
@Configuration
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatConfig {

    private String appId;

    private String appSecret;

    private String token;
}
