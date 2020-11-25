package com.blog.controller;

import com.blog.pojo.Paper;
import com.blog.pojo.User;
import com.blog.service.BlogService;
import com.blog.service.impl.BlogServiceImpl;
import com.blog.utils.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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

    @RequestMapping("/saveNewpaper")
    public Map<String,String> saveNewpaper(@RequestBody Map<String, String> param, HttpSession session){
        Paper paper = new Paper();
        paper.setId(UUID.randomUUID().toString());
        paper.setPaperPicture(param.get("paperPicture"));
        paper.setPaperText(param.get("paperText"));
        paper.setPapertitle(param.get("paperText"));
        paper.setPaperTextId(UUID.randomUUID().toString());
        paper.setTime(new Date());
        paper.setUserId(((User)session.getAttribute("loginUser")).getId());
        return blogServiceImpl.saveNewpaper(paper);
    }
}
