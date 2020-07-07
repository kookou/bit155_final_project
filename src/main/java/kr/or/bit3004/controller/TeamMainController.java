package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.service.TodoListService;

@Controller
public class TeamMainController {

	@Autowired
	private TodoListService service;
	
	@RequestMapping("teamMain.do")
	public String selectTeamName(int teamNo, Model model) {
		model.addAttribute("todoList", service.selectTodoList(teamNo));
		return "teamMain/teamMain";
	}
	
}
