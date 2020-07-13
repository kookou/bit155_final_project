package kr.or.bit3004.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;

@Controller
public class TodoListController {
	
	@Autowired
	private TodoListService service;
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("todoList.do")
	public String todoList(int teamNo, Model model) {
		model.addAttribute("todoList", service.selectTodoList(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "todoList/include";
	}
	
}
