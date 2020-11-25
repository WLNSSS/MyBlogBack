package com.blog.service;

import com.blog.pojo.Paper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BlogService {
    /**
     * @param uploadFile 文件
     * @Description: 上传文件
     */
    public String uploadPaperPic(MultipartFile uploadFile);

    /**
     * @param paper 文章
     * @Description: 创建新的文章
     */
    public Map<String,String> saveNewpaper(Paper paper);
}
