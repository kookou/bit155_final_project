package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit3004.dto.Mail;
import kr.or.bit3004.service.MailService;
import kr.or.bit3004.service.UserService;

@RestController
public class UserAjaxController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/signup/duplicateCheck.ajax")
	public int emailCheck(String id) {
		System.out.println("emailCheck");
		System.out.println(userService.idCheck(id));
		
		return userService.idCheck(id);
	}
	
	
	@RequestMapping("/signup/confirmEmail.ajax")
	public int sendConfirmEmail(Mail mail) {
		System.out.println("sendConfirmEmail");
		int result = 0;
		
		try {
			result = mailService.sendConfirmEmail(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(result);
		
		return result;
	}


}
