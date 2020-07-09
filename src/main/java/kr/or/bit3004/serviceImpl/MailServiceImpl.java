package kr.or.bit3004.serviceImpl;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dto.Mail;
import kr.or.bit3004.service.MailService;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public int sendConfirmEmail(Mail mail) throws Exception{
        MimeMessage messagedto = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messagedto, true, "UTF-8");
        
        Random random = new Random(System.currentTimeMillis());
        int confirmation = 0;
        
        while(true){
            confirmation = (random.nextInt(10000));
            if(confirmation <10000 && confirmation>1000){break;}
        }
        
        messageHelper.setFrom("3004bit@gmail.com");       
        messageHelper.setTo(mail.getReceiver()); 
        messageHelper.setSubject("ccc : 요청하신 인증번호입니다.");
        messageHelper.setText("요청하신 인증번호는 " + confirmation + "입니다.");
 
        mailSender.send(messagedto);
        
        return confirmation;
    }

	
	

}
