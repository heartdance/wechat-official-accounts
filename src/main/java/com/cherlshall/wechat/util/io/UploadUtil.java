package com.cherlshall.wechat.util.io;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadUtil {

    /**
     * 单文件上传
     * @param file
     * @return 1:成功 0:文件为空 -1:发生异常
     */
    public static int upload(MultipartFile file) {
        if(file.isEmpty()){
            return 0;
        }
        String fileName = file.getOriginalFilename(); // 获得文件名
        // long size = file.getSize(); // 获得文件大小
        String path = "C:/test" ; // 文件的父路径 即文件放置的路径
        File dest = new File(path + "/" + fileName); // 包含文件名的路径 即 文件父路径+文件名
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return 1;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 批量上传
     * @param files
     * @return 上传失败的文件名和错误代码 错误代码参考单文件上传 返回null表示无上传失败文件
     */
    public static Map<String, Integer> upload(List<MultipartFile> files) {
        Map<String, Integer> failureFiles = null;
        for (MultipartFile file : files) {
            int uploadFlag = upload(file);
            if (uploadFlag <= 0) {
                if (failureFiles == null) {
                    failureFiles = new HashMap<>();
                }
                failureFiles.put(file.getOriginalFilename(), uploadFlag);
            }
        }
        return failureFiles;
    }

}
