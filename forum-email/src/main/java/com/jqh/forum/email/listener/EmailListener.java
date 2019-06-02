package com.jqh.forum.email.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * @Auther: 几米
 * @Date: 2019/5/26 14:58
 * @Description: EmailListener
 */
@Component
@Slf4j
@RabbitListener(queues = "email")
public class EmailListener {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    @RabbitHandler
    public void sendEmail(Map<String,String> map){
        String email=map.get("email");
        String checkCode=map.get("checkCode");
        log.trace(email+":"+checkCode);
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailFrom);
            helper.setTo(email);
            helper.setSubject("myForum");
            StringBuffer sb = new StringBuffer();
           sb.append("<!DOCTYPE html>\n" +
                   "<html>\n" +
                   "<head>\n" +
                   "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                   "    <title></title>\n" +
                   "    <meta charset=\"utf-8\" />\n" +
                   "\n" +
                   "</head>\n" +
                   "<body>\n" +
                   "    <div class=\"qmbox qm_con_body_content qqmail_webmail_only\" id=\"mailContentContainer\" style=\"\">\n" +
                   "        <style type=\"text/css\">\n" +
                   "            .qmbox body {\n" +
                   "                margin: 0;\n" +
                   "                padding: 0;\n" +
                   "                background: #fff;\n" +
                   "                font-family: \"Verdana, Arial, Helvetica, sans-serif\";\n" +
                   "                font-size: 14px;\n" +
                   "                line-height: 24px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox div, .qmbox p, .qmbox span, .qmbox img {\n" +
                   "                margin: 0;\n" +
                   "                padding: 0;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox img {\n" +
                   "                border: none;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .contaner {\n" +
                   "                margin: 0 auto;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .title {\n" +
                   "                margin: 0 auto;\n" +
                   "                background: url() #CCC repeat-x;\n" +
                   "                height: 30px;\n" +
                   "                text-align: center;\n" +
                   "                font-weight: bold;\n" +
                   "                padding-top: 12px;\n" +
                   "                font-size: 16px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .content {\n" +
                   "                margin: 4px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .biaoti {\n" +
                   "                padding: 6px;\n" +
                   "                color: #000;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xtop, .qmbox .xbottom {\n" +
                   "                display: block;\n" +
                   "                font-size: 1px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb1, .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                   "                display: block;\n" +
                   "                overflow: hidden;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb1, .qmbox .xb2, .qmbox .xb3 {\n" +
                   "                height: 1px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                   "                border-left: 1px solid #BCBCBC;\n" +
                   "                border-right: 1px solid #BCBCBC;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb1 {\n" +
                   "                margin: 0 5px;\n" +
                   "                background: #BCBCBC;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb2 {\n" +
                   "                margin: 0 3px;\n" +
                   "                border-width: 0 2px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb3 {\n" +
                   "                margin: 0 2px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xb4 {\n" +
                   "                height: 2px;\n" +
                   "                margin: 0 1px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .xboxcontent {\n" +
                   "                display: block;\n" +
                   "                border: 0 solid #BCBCBC;\n" +
                   "                border-width: 0 1px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .line {\n" +
                   "                margin-top: 6px;\n" +
                   "                border-top: 1px dashed #B9B9B9;\n" +
                   "                padding: 4px;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .neirong {\n" +
                   "                padding: 6px;\n" +
                   "                color: #666666;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .foot {\n" +
                   "                padding: 6px;\n" +
                   "                color: #777;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .font_darkblue {\n" +
                   "                color: #006699;\n" +
                   "                font-weight: bold;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .font_lightblue {\n" +
                   "                color: #008BD1;\n" +
                   "                font-weight: bold;\n" +
                   "            }\n" +
                   "\n" +
                   "            .qmbox .font_gray {\n" +
                   "                color: #888;\n" +
                   "                font-size: 12px;\n" +
                   "            }\n" +
                   "        </style>\n" +
                   "        <div class=\"contaner\">\n" +
                   "            <div class=\"title\">注册验证</div>\n" +
                   "            <div class=\"content\">\n" +
                   "                <p class=\"biaoti\"><b>亲爱的用户，你好！</b></p>\n" +
                   "                <b class=\"xtop\"><b class=\"xb1\"></b><b class=\"xb2\"></b><b class=\"xb3\"></b><b class=\"xb4\"></b></b>\n" +
                   "                <div class=\"xboxcontent\">\n" +
                   "                    <div class=\"neirong\">\n" +
                   "                        <p><b>验证码：</b><span class=\"font_lightblue\"><span id=\"yzm\" data=\"").append(checkCode).append("\" onclick=\"return false;\" t=\"7\" style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">").append(checkCode).append("</span></span><br><span class=\"font_gray\">(请输入该验证码完成验证，验证码30分钟内有效！)</span></p>\n" +
                   "                        <div class=\"line\">如果你未申请该验证码，请忽略，此为jqh的测试邮件。</div>\n" +
                   "                    </div>\n" +
                   "                </div>\n" +
                   "                <b class=\"xbottom\"><b class=\"xb4\"></b><b class=\"xb3\"></b><b class=\"xb2\"></b><b class=\"xb1\"></b></b>\n" +
                   "                <p class=\"foot\">如果仍有问题，请联系QQ: <span data=\"862965251\" onclick=\"return false;\" t=\"7\" style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">862965251\n" +
                   "</span></p>\n" +
                   "            </div>\n" +
                   "        </div>\n" +
                   "        <style type=\"text/css\">\n" +
                   "            .qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {\n" +
                   "                display: none !important;\n" +
                   "            }\n" +
                   "        </style>\n" +
                   "    </div>\n" +
                   "</body>\n" +
                   "</html>");

            helper.setText(sb.toString(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }
}
