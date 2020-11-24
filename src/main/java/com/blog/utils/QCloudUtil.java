package com.blog.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * @Classname QCloudUtil
 * @Description TODO
 * @Date 2020/11/23 11:27
 * @Created by wlnsss
 */
@Service
public class QCloudUtil {

    @Value("${qcloud.cos.bucketName}")
    private String bucketName;
    @Value("${qcloud.cos.secretId}")
    private String secretId;
    @Value("${qcloud.cos.secretKey}")
    private String secretKey;
    @Value("${qcloud.cos.regionName}")
    private String regionName;
    // 腾讯云 自定义文件夹名称
    @Value("${qcloud.cos.prefix}")
    private String prefix;
    // 访问域名
    @Value("${qcloud.cos.url}")
    public String url;

    public String uploadFile(MultipartFile file){
    // 创建COS 凭证
    COSCredentials credentials = new BasicCOSCredentials(secretId,secretKey);
    // 配置 COS 区域 就购买时选择的区域
    ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        Map<String,String> result = new HashMap<String, String>();
        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials,clientConfig);
        String fileName = file.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            file.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // 将 文件上传至 COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        return url+fileName;
    }

    /**
     * 删除文件
     * @param key 文件名
     * @return 成功与否
     */
    public Map<String,String> deleteFile(String key){
        Map<String,String> result = new HashMap<String, String>();
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        cosClient.deleteObject(bucketName, key);
        cosClient.shutdown();
        return result;
    }

}
