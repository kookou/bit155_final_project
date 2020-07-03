package kr.or.bit3004.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class todoListController {
	
	@RequestMapping("todoList.do")
	public String todoList() {
		return "todoList/include";
	}
	
}
