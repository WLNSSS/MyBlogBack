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

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Classname QCloudUtil
 * @Description TODO
 * @Date 2020/11/23 11:27
 * @Created by wlnsss
 */
public class QCloudUtil {

    @Value("${qcloud.cos.bucketName}")
    private static String bucketName;
    @Value("${qcloud.cos.secretId}")
    private static String secretId;
    @Value("${qcloud.cos.secretKey}")
    private static String secretKey;
    @Value("${qcloud.cos.regionName}")
    private static String regionName;

    /**
     * 上传文件到bucket
     *
     * @param in     文件流
     * @param uploadName 上传文件名
     * @return 访问地址
     */
    public static Map<String,String> uploadFile(InputStream in, String uploadName, Long contentLength){
        Map<String,String> result = new HashMap<String, String>();
        // 生成图片的唯一标识名
        String uuid = UUID.randomUUID().toString();
        int pointIndex = uploadName.lastIndexOf('.');
        // 图片名有后缀，没有就有问题了
        if (pointIndex == -1) {
            result.put("errorInfo","上传文件类型出错");
            return result;
        }
        // 生成最终的图片名
        String filename = uuid + uploadName.substring(pointIndex);
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        ObjectMetadata metadata = new ObjectMetadata();
        Date expiration = new Date(new Date(System.currentTimeMillis()).getTime() + 3600L * 1000 * 24 * 365 * 100);
        metadata.setContentLength(contentLength);
        metadata.setExpirationTime(expiration);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,filename,in,metadata);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
        cosclient.shutdown();
        //通过以下地址可访问到上传的图片
        result.put("url","https://" + bucketName + ".cos." + regionName +".myqcloud.com/" + filename);
        return result;
    }

}
