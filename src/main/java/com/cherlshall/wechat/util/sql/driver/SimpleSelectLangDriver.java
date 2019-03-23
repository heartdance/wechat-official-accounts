package com.cherlshall.wechat.util.sql.driver;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleSelectLangDriver extends XMLLanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            String whereScript = CommonSelectLangDriver.generateWhereScript(parameterType);
            script = "<script>" + matcher.replaceAll(whereScript) + "</script>";
        }
        return super.createSqlSource(configuration, script, parameterType);
    }
}
