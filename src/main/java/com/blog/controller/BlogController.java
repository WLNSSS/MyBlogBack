package com.blog.controller;

import com.blog.service.BlogService;
import com.blog.service.impl.BlogServiceImpl;
import com.blog.utils.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Classname BlogController
 * @Description 博客基本操作
 * @Date 2020/11/23 17:27
 * @Created by wlnsss
 */
@Controller
@ResponseBody
public class BlogController {
    @Autowired
    BlogServiceImpl blogServiceImpl;
    @RequestMapping("/upload")
    public String uploadPaperPic(@RequestParam("file")MultipartFile uploadFile){
        return blogServiceImpl.uploadPaperPic(uploadFile);
    }
}
