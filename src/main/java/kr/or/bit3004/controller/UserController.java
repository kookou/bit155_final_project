package kr.or.bit3004.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@RequestMapping("/signin")
	public String signIn() {
		return "user/signIn";
	}
	
	@RequestMapping("/signup")
	public String signUp() {
		return "user/signUp";
	}
	
	@RequestMapping("/resetpassword")
	public String resetPassword() {
		return "user/resetPassword";
	}
	
	@RequestMapping("/edituserinfo")
	public String editUserInfo() {
		return "user/editUserInfo";
	}
	
}
