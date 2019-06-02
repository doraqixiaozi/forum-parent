package com.jqh.forum.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.jqh.forum.sms.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: 几米
 * @Date: 2019/5/25 23:13
 * @Description: SMSListener
 */
@Component
@RabbitListener(queues = "sms")
@Slf4j
public class SMSListener {
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.TemplateCode}")
    private String TemplateCode;
    @Value("${aliyun.sms.SignName}")
    private String SignName;

    @RabbitHandler
    public void sendCheckCode(Map<String,String> map){
        String mobile = map.get("mobile");
        String checkCode = map.get("checkCode");
        try {
            smsUtil.sendSms(mobile,SignName,TemplateCode,"{\"checkCode\":\""+checkCode+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
