package kr.or.bit3004.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.bit3004.aside.AsideService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private MailService mailService;
	
	@Autowired	
	private AsideService asideService;
	
	//로그인 폼
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String signIn() {
		return "user/signIn";
	}

	
	
	// 가입 폼
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signUp() {
		return "user/signUp";
	}
	

	//가입 처리 
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signUp(User user) {
		System.out.println("controller");
		System.out.println(user);
		
		service.insertUser(user);
		return "redirect:signin";
	}
	
	
	
	// 비번 수정 요청 폼
	@RequestMapping(value="/forgotpwd", method=RequestMethod.GET)
	public String resetPassword() {
		return "user/forgotPwd";

	}
	
	// 비번 수정 처리
	// 이메일로 비밀번호 수정 페이지 링크를 메일로 보내기 
	//		>	링크를 클릭하면 비밀번호 수정 페이지 
	//		>	해당 페이지에서 새 비밀번호를 입력하면 DB에 반영 
	
	//회원 수정 폼
	@RequestMapping(value="/edituser", method=RequestMethod.GET)
	public String editUserInfo(String teamNo, Model model) {		
		
		System.out.println("editUserInfo");
		
		if(teamNo != null) {
			System.out.println("teamNo : "+teamNo);
			int intTeamNo = Integer.parseInt(teamNo);
			   model.addAttribute("teamNo", teamNo);
			   System.out.println("team : "+ asideService.getTeam(intTeamNo));
			   model.addAttribute("team", asideService.getTeam(intTeamNo));
			   model.addAttribute("teamMember", asideService.getTeamMember(intTeamNo));
			   model.addAttribute("allBoardList", asideService.getAllBoardList(intTeamNo));
		}

		return "user/editUser";
	}
	
	
	//회원 수정 처리
	@RequestMapping(value="/edituser", method=RequestMethod.POST)
	public String editUserInfo(User user, HttpServletRequest request, HttpSession session) {
		System.out.println("===controller===");
		System.out.println(user);
		
		service.updateUser(user, session);

		
		return "redirect:edituser?teamNo=";
	}
	
	//회원 삭제 : ROLE_MEMBER 테이블에서 먼저 지우면 TRIGGER로 USER 테이블 데이터가 지워진다
	@RequestMapping(value="/deleteuser", method=RequestMethod.GET)
	public String deleteUser(String id, HttpSession session) {
		service.deleteUser(id, session);
		return "redirect:signin"; 
	}

	
}
