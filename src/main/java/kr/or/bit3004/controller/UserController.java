package kr.or.bit3004.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit3004.dto.User;
//import kr.or.bit3004.service.UserService;
import kr.or.bit3004.serviceImpl.UserServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	private UserServiceImpl service;
	
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
	@RequestMapping(value="/resetpassword", method=RequestMethod.GET)
	public String resetPassword() {
		return "user/resetPassword";

	}
	
	// 비번 수정 처리
	// 이메일로 비밀번호 수정 페이지 링크를 메일로 보내기 
	//		>	링크를 클릭하면 비밀번호 수정 페이지 
	//		>	해당 페이지에서 새 비밀번호를 입력하면 DB에 반영 
	
	//회원 수정 폼
	@RequestMapping(value="/edituserinfo", method=RequestMethod.GET)
	public String editUserInfo() {
		return "user/editUserInfo";
	}
	
	//회원 수정 처리
	@RequestMapping(value="/edituserinfo", method=RequestMethod.POST)
	public String editUserInfo(User user, HttpServletRequest request) {
		service.updateUser(user, request);
		return "redirect:edituserinfo";
	}
	
	//회원 삭제 : ROLE_MEMBER 테이블에서 먼저 지우면 TRIGGER로 USER 테이블 데이터가 지워진다
	@RequestMapping(value="/deleteuser", method=RequestMethod.GET)
	public String deleteUser(String id) {
		service.deleteUser(id);
		return "redirect:signin";
	}

	
}
