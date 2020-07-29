package kr.or.bit3004.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit3004.aside.AsideService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired	
	private AsideService asideService;
	

	
	
	//로그인 폼 이동
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String signIn() {
		return "user/signIn";
	}

	
	
	// 가입 폼 이동
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signUp() {
		return "user/signUp";
	}
	

	/**
    * @Method Name : signUp (가입 처리)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : 사용자에게 입력받은 정보로 만들어진 User 객체를 DB에 저장해서 가입처리
    * @param : User user
    * @return : String
    **/
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signUp(User user) {
		service.insertUser(user);
		return "redirect:signin";
	}
	
	
	
	/**
    * @Method Name : editUserInfo (회원정보 수정 폼 이동)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : session에서 로그인된 사용자 정보를 가져와서 model에 필요한 값들을 저장한 후 회원정보 수정 페이지로 이동
    * @param : HttpSession session
    * @param : Model model
    * @return : String
    **/
	@RequestMapping(value="/edituser", method=RequestMethod.GET)
	public String editUserInfo(HttpSession session, Model model) {
		
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		int teamNo = currentUser.getTeamNo();
		
		if(teamNo > 0) {
			   model.addAttribute("teamNo", teamNo);
			   model.addAttribute("team", asideService.getTeam(teamNo));
			   model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
			   model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		}
		return "user/editUser";
	}
	
	
	/**
    * @Method Name : editUserInfo (회원정보 수정 처리)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : 사용자한테 입력받은 정보로 DB 회원정보를 수정
    * @param : User user
    * @param : HttpServletRequest request
    * @param : HttpSession session
    * @return : String
    */
	@RequestMapping(value="/edituser", method=RequestMethod.POST)
	public String editUserInfo(User user, HttpServletRequest request, HttpSession session) {
		service.updateUser(user, session);
		return "redirect:edituser";
	}
	
	
	/**
    * @Method Name : deleteUser (회원 탈퇴 처리)
    * @작성자 : 김선
    * @변경이력 :
    * @Method 설명 : 회원 탈퇴 처리
    * @param : String id
    * @param : HttpSession session
    * @return : String
    */
	@RequestMapping(value="/deleteuser", method=RequestMethod.GET)
	public String deleteUser(String id, HttpSession session) {
		service.deleteUser(id, session);
		return "redirect:signin"; 
	}
}
