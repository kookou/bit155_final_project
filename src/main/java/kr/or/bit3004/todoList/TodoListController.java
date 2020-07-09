package kr.or.bit3004.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TodoListController {
	
	@Autowired
	private TodoListService service;
	
	@RequestMapping("todoList.do")
	public String todoList(int teamNo, Model model) {
		model.addAttribute("todoList", service.selectTodoList(teamNo));
		return "todoList/include";
	}
	
}
