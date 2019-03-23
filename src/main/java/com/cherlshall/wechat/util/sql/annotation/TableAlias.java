package com.cherlshall.wechat.util.sql.annotation;

import com.cherlshall.wechat.util.sql.enums.SymbolEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * where时根据这个注解来对字段添加别名
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableAlias {
    SymbolEnum value();
}
