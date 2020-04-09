package com.jqh.forum.controller;

import entity.Result;
import entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.IdWorker;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * ImageController
 *
 * @author 862965251@qq.com
 * @date 2020-04-08 13:45
 */
@CrossOrigin
@RestController
@Slf4j
public class FileController {
    @Resource
    private HttpServletRequest request;
    @Value(value = "${image.url.prefix}")
    private String prefix;
    @Resource
    private IdWorker idWorker;

    /**
     * 上传图片
     *
     * @param from  来自哪个项目
     * @param image 图片
     * @return 图片保存地址
     */
    @PostMapping("/image/{from}")
    public Result uploadImage(@PathVariable String from, MultipartFile image) {
        //获取文件后缀
        String suffix = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."), image.getOriginalFilename().length());
        //获取新名称
        long newID = idWorker.nextId();
        //获取项目动态绝对路径
        StringBuilder filePath = new StringBuilder(request.getServletContext().getRealPath("")).append("/image/").append(from);
        //保存后返回的url路径
        StringBuilder urlPath = new StringBuilder(prefix).append("/image/").append(from);
        File savePathFile = new File(filePath.toString());
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdirs();
        }
        String newPath = new String("/" + newID + suffix);
        try {
            image.transferTo(new File(filePath.append(newPath).toString()));
        } catch (IOException e) {
            log.error("图片保存出错");
            throw new RuntimeException(e);
        }
        return new Result(true, StatusCode.OK, "上传成功", urlPath.append(newPath).toString());
    }
}
