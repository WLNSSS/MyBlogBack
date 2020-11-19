package com.blog.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.blog.pojo.User;
import com.blog.service.impl.AliyunSmsSenderServiceImpl;
import com.blog.service.impl.UserServiceImpl;
import com.blog.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@ResponseBody
public class UserController {
    @Autowired
    private Producer captchaProducer = null;
    @Autowired
    private AliyunSmsSenderServiceImpl aliyunSmsSenderServiceImpl;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RedisUtils redisUtils;
    @RequestMapping("/hello")
    public Map<String,Object> hello(@RequestBody Map<String, Object> map) {
        return map;
    }

    @RequestMapping("/createImageCode")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            request.getSession().setAttribute("imageCode", createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @RequestMapping("/sms")
    public String sms(@RequestBody Map<String, String> param) {
        Map<String, String> map = new HashMap<String, String>();
        String code = String.valueOf((int)((Math.random()*9+1)*1000));
        map.put("code", code);
        SendSmsResponse sendSmsResponse = aliyunSmsSenderServiceImpl.sendSms(param.get("moblieNumber"),
                JSON.toJSONString(map),
                "SMS_205462371");
        if(sendSmsResponse.getCode().equals("OK")){
            redisUtils.set(param.get("uuid"),code,5L, TimeUnit.MINUTES);
        }
        return JSON.toJSONString(sendSmsResponse);
    }

    @RequestMapping("/registerUser")
    public Map<String, List<String>> registerUser(@RequestBody Map<String, String> param, HttpSession session) {
        Map<String, List<String>> returnData = new HashMap<String, List<String>>();
        User user = new User();
        String validCode = (String)redisUtils.get(param.get("uuid"));
        user.setId(UUID.randomUUID().toString());
        user.setAccount(param.get("account"));
        user.setPassword(param.get("password"));
        user.setUserName(param.get("userName"));
        user.setPhoneNumber(param.get("moblieNumber"));
        returnData = userService.existsUserInfo(user);
        if(returnData.get("errorInfo").size() > 0){
            return returnData;
        }
        List<String> errorInfo = new ArrayList<String>();
        if(param.get("messageNumber").equals(validCode)) {
            try {
                userService.register(user);
                session.setAttribute("loginUser",user.getUserName());
            }catch (Exception e){
                errorInfo.add("数据库插入失败！");
                returnData.put("errorInfo",errorInfo);
            }
        }else{
            errorInfo.add("验证码输入有误！");
            returnData.put("errorInfo",errorInfo);
        }
            return returnData;
    }
}
