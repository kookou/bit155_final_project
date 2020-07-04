package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.service.TodoListService;

@Controller
public class todoListController {
	
	@Autowired
	private TodoListService service;
	
	@RequestMapping("todoList.do")
	public String todoList(int teamNo, Model model) {
		model.addAttribute("todoList", service.selectTodoList(teamNo));
		return "todoList/include";
	}
	
	@RequestMapping("todoList2.do")
	public String todoList2(int teamNo, Model model) {
		model.addAttribute("todoList", service.selectTodoList(teamNo));
		return "jsonView";
	}
	
}
