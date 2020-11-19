package com.blog.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * @Classname AliyunSmsSenderService
 * @Description 阿里云短信发送接口
 * @Date 2020/11/16 10:04
 * @Created by wlnsss
 */
public interface AliyunSmsSenderService {

    /**
     * @param phoneNumbers:      手机号
     * @param templateParamJson: 模板参数json {"sellerName":"123456","orderSn":"123456"}
     * @param templateCode:      阿里云短信模板code
     * @Description: 对接阿里云短信服务实现短信发送
     */
    SendSmsResponse sendSms(String phoneNumbers, String templateParamJson, String templateCode);

    /**
     * @param bizId:       短信对象的对应的bizId
     * @param phoneNumber: 手机号
     * @param pageSize:    分页大小
     * @param currentPage: 当前页码
     * @Description: 查询发送短信的内容
     */
    QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber, Long pageSize, Long currentPage);
}
