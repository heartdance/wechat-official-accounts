package com.cherlshall.wechat.util.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    /**
     * 将文件输入流转化为String
     */
    public static String convertStreamToString(InputStream inputStream){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 将json文件转为JSONString
     * @param path resources的子路径
     */
    public static String readJsonStringFromResources(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        if (resource.exists()) {
            return convertStreamToString(resource.getInputStream());
        } else {
            throw new IOException();
        }
    }

    /**
     * 将json文件转为JSONObject
     * @param path resources的子路径
     */
    public static JSONObject readJsonObjectFromResources(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        if (resource.exists()) {
            return JSON.parseObject(convertStreamToString(resource.getInputStream()));
        } else {
            throw new IOException();
        }
    }

    /**
     * 将json文件转为JSONArray
     * @param path resources的子路径
     */
    public static JSONArray readJsonArrayFromResources(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        if (resource.exists()) {
            return JSON.parseArray(convertStreamToString(resource.getInputStream()));
        } else {
            throw new IOException();
        }
    }

}
