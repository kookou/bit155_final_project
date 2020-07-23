package kr.or.bit3004.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public int sendConfirmEmail(Mail mail, Model model) {
		System.out.println("sendConfirmEmail");
		System.out.println(mail);
		int result = 0;
		
		try {
//			result = 
					mailService.sendSimpleMessage(mail, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(result);
		
		return result;
	}


}
