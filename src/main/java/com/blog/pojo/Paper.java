package com.blog.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Classname paper
 * @Description 文章实体
 * @Date 2020/11/23 17:35
 * @Created by wlnsss
 */
@Data
@ToString
public class Paper {
    private String id;
    private String userId;
    private Date time;
    private String paperPicture;
    private String paperTextId;
    private String paperText;
    private String papertitle;
}
