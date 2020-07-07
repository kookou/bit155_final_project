package kr.or.bit3004.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KanbanCoontroller {
	
	@RequestMapping("/include.do")
	public String home() {
		return "kanban/kanban";
	}
	
}
