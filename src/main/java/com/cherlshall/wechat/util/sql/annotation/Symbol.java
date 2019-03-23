package com.cherlshall.wechat.util.sql.annotation;

import com.cherlshall.wechat.util.sql.enums.SymbolEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用此注解排除不需要作为sql参数的字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Symbol {
    SymbolEnum value();
}
