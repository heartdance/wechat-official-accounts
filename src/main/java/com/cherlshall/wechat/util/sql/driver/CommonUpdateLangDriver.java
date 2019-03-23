package com.cherlshall.wechat.util.sql.driver;

import com.cherlshall.wechat.util.sql.annotation.Invisible;
import com.cherlshall.wechat.util.sql.annotation.UpdateSet;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUpdateLangDriver extends XMLLanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        StringBuilder scriptBuilder = new StringBuilder();
        if (matcher.find()) {
            StringBuilder setScriptBuilder = new StringBuilder();
            // 生成set部分sql
            setScriptBuilder.append("<set>");
            for (Field field : parameterType.getDeclaredFields()) {
                // 排除被@Invisible注解的属性
                if (!field.isAnnotationPresent(Invisible.class) && field.isAnnotationPresent(UpdateSet.class)) {
                    String tmp = "<if test=\"_field != null\">_column=#{_field},</if>";
                    setScriptBuilder.append(tmp.replaceAll("_field",field.getName())
                            .replaceAll("_column", CaseFormat.LOWER_CAMEL
                                    .to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
                }
            }
            setScriptBuilder.deleteCharAt(setScriptBuilder.lastIndexOf(","));
            setScriptBuilder.append("</set>");
            // 生成条件部分sql
            String whereScript = generateWhereScript(parameterType);
            scriptBuilder.append("<script>").append(setScriptBuilder.toString())
                    .append(whereScript).append("</script>");

        }
        return super.createSqlSource(configuration, scriptBuilder.toString(), parameterType);
    }

    // 根据实体类自动生成sql的动态where部分
    private String generateWhereScript(Class<?> parameterType) {
        StringBuilder whereScriptBuilder = new StringBuilder();
        whereScriptBuilder.append("<where>");
        for (Field field : parameterType.getDeclaredFields()) {
            // 排除被@Invisible注解的属性
            if (!field.isAnnotationPresent(Invisible.class) && field.isAnnotationPresent(UpdateSet.class)) {
                CommonSelectLangDriver.handleWhereItem(whereScriptBuilder, field);
            }
        }
        whereScriptBuilder.append("</where>");
        return whereScriptBuilder.toString();
    }
}
