package kr.or.bit3004.user;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.ui.Model;

import freemarker.template.TemplateException;

public interface MailService {
	public int sendSimpleMessage(Mail mail, Model model) throws MessagingException, IOException, TemplateException;

}
