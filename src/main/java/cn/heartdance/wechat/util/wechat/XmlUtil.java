package cn.heartdance.wechat.util.wechat;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 解析xml请求 生成xml响应
 */
public class XmlUtil {

    /**
     * 解析微信发来的请求(xml)
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception {
        // 从request中取得输入流
        try(InputStream inputStream = request.getInputStream()) {
            // 将解析结果存储在HashMap中
            Map<String,String> map = new HashMap<>();
            // 读取输入流
            SAXReader reader = new SAXReader();
            // 得到xml文档
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList)
                map.put(e.getName(), e.getText());
            // 释放资源
            inputStream.close();
            return map;
        }
    }

    /**
     * 将map转化成xml响应给微信服务器
     */
    static String mapToXML(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXML2(map, sb);
        sb.append("</xml>");
        try {
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static void mapToXML2(Map<?, ?> map, StringBuffer sb) {
        Set<?> keys = map.keySet();
        for (Object key : keys) {
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value instanceof List<?> listValue) {
                sb.append("<").append(key).append(">");
                for (Object o1 : listValue) {
                    Map<?, ?> hm = (Map<?, ?>) o1;
                    mapToXML2(hm, sb);
                }
                sb.append("</").append(key).append(">");

            } else {
                if (value instanceof Map<?, ?> mapValue) {
                    sb.append("<").append(key).append(">");
                    mapToXML2(mapValue, sb);
                    sb.append("</").append(key).append(">");
                } else {
                    sb.append("<").append(key).append("><![CDATA[").append(value).append("]]></").append(key).append(">");
                }

            }

        }
    }

}