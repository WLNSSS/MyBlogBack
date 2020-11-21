package com.blog.mapper;

import com.blog.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Classname userMapper
 * @Description TODO
 * @Date 2020/11/18 11:45
 * @Created by wlnsss
 */
@Mapper
public interface UserMapper {
    @Insert({"insert into user(id,username,account,password,phonenumber) values(#{id},#{userName},#{account},#{password},#{phoneNumber})"})
    public void registerUser(User user);
    @Select({" select count(id) from user where username = #{userName}"})
    public int existsUserName(User user);
    @Select({" select count(id) from user where account = #{account}"})
    public int existsUserAccount(User user);
    @Select({" select count(id) from user where phoneNumber = #{phoneNumber}"})
    public int existsUserPhone(User user);
    @Select({" select account,username,phonenumber from user where account = #{account} and password = #{password}"})
    public User login(@Param("account") String account, @Param("password")String password);
}
