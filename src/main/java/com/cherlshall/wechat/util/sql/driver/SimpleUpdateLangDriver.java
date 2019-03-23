package com.cherlshall.wechat.util.sql.driver;

import com.cherlshall.wechat.util.sql.annotation.Invisible;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleUpdateLangDriver extends XMLLanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder setScriptBuilder = new StringBuilder();
            setScriptBuilder.append("<set>");
            for (Field field : parameterType.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Invisible.class)) {
                    String tmp = "<if test=\"_field != null\">_column=#{_field},</if>";
                    setScriptBuilder.append(tmp.replaceAll("_field", field.getName())
                            .replaceAll("_column", CommonSelectLangDriver.getColumnName(field)));
                }
            }
            setScriptBuilder.deleteCharAt(setScriptBuilder.lastIndexOf(","));
            setScriptBuilder.append("</set>");
            script = "<script>" + matcher.replaceAll(setScriptBuilder.toString()) + "</script>";
        }
        return super.createSqlSource(configuration, script, parameterType);
    }
}
