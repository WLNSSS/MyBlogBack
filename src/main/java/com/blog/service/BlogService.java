package com.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BlogService {
    /**
     * @param uploadFile 文件
     * @Description: 注册用户
     */
    public String uploadPaperPic(MultipartFile uploadFile);
}
