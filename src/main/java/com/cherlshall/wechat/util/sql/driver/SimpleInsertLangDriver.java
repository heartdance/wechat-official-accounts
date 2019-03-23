package com.cherlshall.wechat.util.sql.driver;

import com.cherlshall.wechat.util.sql.annotation.Invisible;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleInsertLangDriver extends XMLLanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        StringBuilder scriptBuilder = new StringBuilder();
        if (matcher.find()) {
            StringBuilder columnsBuilder = new StringBuilder();
            StringBuilder valuesBuilder = new StringBuilder();
            columnsBuilder.append("(");
            valuesBuilder.append("(");
            for (Field field : parameterType.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Invisible.class)) {
                    columnsBuilder.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                            field.getName()) + ",");
                    valuesBuilder.append("#{" + field.getName() + "},");
                }
            }
            columnsBuilder.deleteCharAt(columnsBuilder.lastIndexOf(","));
            valuesBuilder.deleteCharAt(valuesBuilder.lastIndexOf(","));
            columnsBuilder.append(")");
            valuesBuilder.append(")");
            scriptBuilder.append("<script>" + matcher.replaceAll(
                    columnsBuilder.toString() + " values " + valuesBuilder.toString()) + "</script>");
        }
        return super.createSqlSource(configuration, scriptBuilder.toString(), parameterType);
    }
}
