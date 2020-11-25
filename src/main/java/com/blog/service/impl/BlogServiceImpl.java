package com.blog.service.impl;

import com.blog.mapper.BlogMapper;
import com.blog.pojo.Paper;
import com.blog.service.BlogService;
import com.blog.utils.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname BlogServiceImp
 * @Description TODO
 * @Date 2020/11/23 18:17
 * @Created by wlnsss
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    QCloudUtil qCloudUtil;
    @Autowired
    private BlogMapper blogMapper;
    /**
     * @param uploadFile 文件
     * @Description: 注册用户
     */
    @Override
    public String uploadPaperPic(MultipartFile uploadFile) {
        return qCloudUtil.uploadFile(uploadFile);
    }

    /**
     * @param paper 文章
     * @Description: 创建新的文章
     */
    @Override
    public Map<String,String> saveNewpaper(Paper paper) {
        Map<String,String> result = new HashMap<String, String>();
        blogMapper.InsertPaperText(paper);
        blogMapper.InsertPaper(paper);
        return result;
    }
}
