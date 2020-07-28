package kr.or.bit3004.user;

import javax.servlet.http.HttpSession;

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
	
	
	
	/*
    * @Method Name : emailCheck (이메일 중복 체크 처리)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : 가입시 사용자에게 입력받은 id값(이메일)이 DB에 이미 존재하는지 확인
    * @param : String id
    * @return : Integer
    */
	@RequestMapping("/signup/duplicateCheck.ajax")
	public int emailCheck(String id) {
		return userService.idCheck(id);
	}
	
	/*
    * @Method Name : sendConfirmEmail (인증메일 전송처리)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : 가입시 사용자에게 입력받은 이메일주소로 인증번호 전송
    * @param : Mail mail
    * @param : Model model
    * @return : Integer
    */
	@RequestMapping("/signup/confirmEmail.ajax")
	public int sendConfirmEmail(Mail mail, Model model) {
		int result = 0;
		
		try {
			result = mailService.sendSimpleMessage(mail, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/*
    * @Method Name : editPassword (비밀번호 수정 처리)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : 사용자에게 기존 비밀번호와 새 비밀번호를 입력받아 경우에 따라 처리 결과를 리턴
    * @param : String pwd
    * @param : String newPwd
    * @param : HttpSession session
    * @return : String
    */
	@RequestMapping("/editUser/editPwd.ajax")
	public String editPassword(String pwd, String newPwd, HttpSession session) {		
		String result = userService.updateUserPwd(pwd, newPwd, session);
		return result;
	}


}
