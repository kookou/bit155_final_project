package kr.or.bit3004.user;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private Configuration freemarkerConfig;

    public int sendSimpleMessage(Mail mail, Model model) throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        
      Random random = new Random(System.currentTimeMillis());
      int confirmation = 0;
      
      while(true){
          confirmation = (random.nextInt(10000));
          if(confirmation <10000 && confirmation>1000){break;}
      }
     
      	model.addAttribute("id", mail.getTo());
      	model.addAttribute("confirmation", Integer.toString(confirmation));

        Template t = freemarkerConfig.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject("TIKA : 요청하신 인증번호입니다");
        helper.setFrom("3004bit@gmail.com");

        mailSender.send(message);
        
        return confirmation;
    }

}
