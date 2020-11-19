package com.blog.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Classname SmsQuery
 * @Description 短信查询实体
 * @Date 2020/11/16 10:02
 * @Created by wlnsss
 */
@Data
@ToString
public class SmsQuery {
    private String bizId;
    private String phoneNumber;
    private Date sendDate;
    private Long pageSize;
    private Long currentPage;
}
