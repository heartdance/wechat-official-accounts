package cn.heartdance.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 图灵机器人的配置参数
 */
@Configuration
@ConfigurationProperties(prefix = "turing")
@Data
public class TuringConfig {
    /**
     * 图灵请求接口
     */
    private String url = "http://www.tuling123.com/openapi/api";
    /**
     * 机器人唯一标识 前往图灵官网注册可免费创建
     */
    private String key;
}
