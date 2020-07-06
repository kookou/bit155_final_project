package kr.or.bit3004.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit3004.dto.TodoList;
import kr.or.bit3004.service.TodoListService;

@RestController
public class TodoListAjaxController {
	
	@Autowired
	private TodoListService service;
	
	@RequestMapping("todoList2.do")
	public List<TodoList> todoList2(int teamNo) {
		return service.selectTodoList(teamNo);
	}
	
	@RequestMapping("insertTodoTitle.do")
	public void insertTodoTitle(TodoList todoList) {
		System.out.println(todoList);
		//service.insertTodoTitle(todoList);
	}
	
}
