package com.forum.controller;

import entity.Result;
import entity.StatusCode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import com.aliyun.oss.model.PutObjectRequest;

import util.IdWorker;


import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class OSSController {


    @Value(value = "${image.url.prefix}")
    private String prefix;

    @Value(value = "${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value(value = "${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value(value ="${aliyun.oss.endpoint}")
    private String endpoint;

    @Value(value ="${aliyun.oss.bucketName}")
    private String bucket;



    public static final String BUSINESS_NAME = "文件上传";

    @Resource
    private HttpServletRequest request;

    @Resource
    private IdWorker idWorker;



    /*
     * 简单上传
     * */
    @PostMapping("/image/{from}")
    public Result fileUpload(@PathVariable String from,@RequestParam MultipartFile image)throws Exception {

        /*FileUseEnum useEnum = FileUseEnum.getByCode(use);*/
        long key = idWorker.nextId();
        System.out.println("upup");
       String fileName = image.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String dir = "ZRFfile";
                //useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
//        String content = "Hello OSS";
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(image.getBytes()));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

//        LOG.info("保存文件记录开始");
//        fileDto.setPath(path);
//        fileService.save(fileDto);
        //上传至oss的文件路径
        String const_path="https://zrf-pic-storage.oss-cn-shenzhen.aliyuncs.com"+"/"+dir+"/"+key+"."+suffix;
        System.out.println("upup");
        return new Result(true, StatusCode.OK, "上传成功", const_path);

    }
}
