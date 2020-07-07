package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.service.KanbanService;

@Controller
public class KanbanCoontroller {
	
	@Autowired
	private KanbanService service;
	
	@RequestMapping("/kanban.do")
	public String kanbanList(int teamNo, Model model) {
		model.addAttribute("kanbanList" , service.allKanbanList(teamNo));
		return "kanban/kanban";
	}
	
	
}
