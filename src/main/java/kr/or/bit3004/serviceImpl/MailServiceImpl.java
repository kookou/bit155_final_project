package kr.or.bit3004.serviceImpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dto.Mail;
import kr.or.bit3004.service.MailService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService{
	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "3004bit@gmail.com";
	
	
	@Override
	public void mailSend(Mail mail) {
		
		System.out.println("mailSend");
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getAddress());
		message.setFrom(MailServiceImpl.FROM_ADDRESS);
		message.setSubject(mail.getTitle());
		message.setText(mail.getMessage());
		
		mailSender.send(message);
		
	}

}
