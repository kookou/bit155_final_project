package kr.or.bit3004.todoList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoListAjaxController {
	
	@Autowired
	private TodoListService service;
	
	@RequestMapping("getTodoContent.do")
	public List<TodoList> todoList(int teamNo) {
		return service.selectTodoList(teamNo);
	}
	
	@RequestMapping("getTodoTitle.do")
	public List<TodoList> getTodoTitle(int teamNo) {
		return service.selectTodoTitle(teamNo);
	}
	
	@RequestMapping("insertTodoTitle.do")
	public void insertTodoTitle(TodoList todoList) {
		System.out.println(todoList);
		//service.insertTodoTitle(todoList);
	}
	
}
