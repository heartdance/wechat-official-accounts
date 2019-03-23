package com.cherlshall.wechat.util.sql.driver;

import com.cherlshall.wechat.util.sql.annotation.Invisible;
import com.cherlshall.wechat.util.sql.annotation.Column;
import com.cherlshall.wechat.util.sql.annotation.Symbol;
import com.cherlshall.wechat.util.sql.annotation.TableAlias;
import com.cherlshall.wechat.util.sql.enums.SymbolEnum;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态生成select语句
 */
public class CommonSelectLangDriver extends XMLLanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        StringBuilder scriptBuilder = new StringBuilder();
        if (matcher.find()) {
            // 生成排序部分sql
            String orderScript = "<if test=\"orderName != null\"> order by ${orderName} " +
                    "<choose><when test=\"orderDirection == 'desc'\">desc</when>" +
                    "<otherwise>asc</otherwise></choose></if>";
            // 生成分页部分sql
            String pageScript = "<if test=\"startIndex != null and pageSize != null\"> " +
                    "limit ${startIndex},${pageSize}</if>";
            // 生成条件部分sql
            String whereScript = generateWhereScript(parameterType);
            scriptBuilder.append("<script>").append(matcher.replaceAll(whereScript))
                    .append(orderScript).append(pageScript).append("</script>");
        }
        return super.createSqlSource(configuration, scriptBuilder.toString(), parameterType);
    }

    // 根据实体类自动生成sql的动态where部分
    static String generateWhereScript(Class<?> parameterType) {
        StringBuilder whereScriptBuilder = new StringBuilder();
        whereScriptBuilder.append("<where>");
        for (Field field : getAllFields(parameterType)) {
            // 排除被@Invisible注解的属性
            if (!field.isAnnotationPresent(Invisible.class)) {
                handleWhereItem(whereScriptBuilder, field, parameterType);
            }
        }
        whereScriptBuilder.append("</where>");
        return whereScriptBuilder.toString();
    }

    // 递归获得类和父类的属性
    static List<Field> getAllFields(Class<?> parameterType) {
        List<Field> fields = new ArrayList<>();
        while (parameterType != Object.class) {
            fields.addAll(Arrays.asList(parameterType.getDeclaredFields()));
            parameterType = parameterType.getSuperclass();
        }
        return fields;
    }

    // where部分对每个字段的处理
    static void handleWhereItem(StringBuilder whereScriptBuilder, Field field) {
        String columnName = getColumnName(field);
        whereScriptBuilder.append(handleField(field, columnName));
    }

    // where部分对每个字段的处理 增加对多表联查时别名的处理
    private static void handleWhereItem(StringBuilder whereScriptBuilder, Field field, Class<?> parameterType) {
        String columnName = getColumnName(field, parameterType);
        whereScriptBuilder.append(handleField(field, columnName));
    }

    // 获得属性在数据库对应的字段名
    static String getColumnName(Field field) {
        String columnName;
        if (field.isAnnotationPresent(Column.class))
            columnName = field.getAnnotation(Column.class).value();
        else
            columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        return columnName;
    }

    // 获得属性在数据库对应的字段名 增加对多表联查时别名的处理
    static String getColumnName(Field field, Class<?> parameterType) {
        String columnName = getColumnName(field);
        if (field.isAnnotationPresent(TableAlias.class))
            columnName = field.getAnnotation(TableAlias.class).value() + "." + columnName;
        else if (parameterType.isAnnotationPresent(TableAlias.class))
            columnName = parameterType.getAnnotation(TableAlias.class).value() + "." + columnName;
        return columnName;
    }

    // where部分对每个字段的标签拼接
    private static String handleField(Field field, String columnName) {
        String tmp;
        if (field.getType().isAssignableFrom(List.class)) {
            // 遇到list类型的属性使用in
            tmp = "<if test=\"_field != null\"> and _column in " +
                    "<foreach collection=\"_field\" item=\"_item\" open=\"(\" separator=\",\" close=\")\">" +
                    "#{_item}" +
                    "</foreach>" +
                    "</if>";
        } else if (field.isAnnotationPresent(Symbol.class)) {
            // 使用自定义运算符
            SymbolEnum symbol = field.getAnnotation(Symbol.class).value();
            switch (symbol) {
                case LIKE: {
                    tmp = "<if test=\"_field != null\"> AND _column like CONCAT('%',#{_field},'%')</if>";
                    break;
                }
                case GT: {
                    tmp = "<if test=\"_field != null\"> AND _column &gt; #{_field}</if>";
                    break;
                }
                case GTE: {
                    tmp = "<if test=\"_field != null\"> AND _column &gt;= #{_field}</if>";
                    break;
                }
                case LT: {
                    tmp = "<if test=\"_field != null\"> AND _column &lt; #{_field}</if>";
                    break;
                }
                case LTE: {
                    tmp = "<if test=\"_field != null\"> AND _column &lt;= #{_field}</if>";
                    break;
                }
                case NE: {
                    tmp = "<if test=\"_field != null\"> AND _column != #{_field}</if>";
                    break;
                }
                default: {
                    tmp = "<if test=\"_field != null\"> AND _column = #{_field}</if>";
                    break;
                }
            }
        } else {
            tmp = "<if test=\"_field != null\"> AND _column = #{_field}</if>";
        }
        return tmp.replaceAll("_field", field.getName()).replaceAll("_column", columnName);
    }
}
