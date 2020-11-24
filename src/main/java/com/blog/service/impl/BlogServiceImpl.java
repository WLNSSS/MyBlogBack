package com.blog.service.impl;

import com.blog.service.BlogService;
import com.blog.utils.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    /**
     * @param uploadFile 文件
     * @Description: 注册用户
     */
    @Override
    public String uploadPaperPic(MultipartFile uploadFile) {
        return qCloudUtil.uploadFile(uploadFile);
    }
}
