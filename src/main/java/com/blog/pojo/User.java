package com.blog.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String id;
    private String userName;
    private String phoneNumber;
    private String account;
    private String password;
}
