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
		return service.selectTodoContent(teamNo);
	}
	
	@RequestMapping("getTodoTitle.do")
	public List<TodoList> getTodoTitle(int teamNo) {
		return service.selectTodoTitle(teamNo);
	}
	
	@RequestMapping("insertTodoTitle.do")
	public int insertTodoTitle(TodoList todoList) {
		service.insertTodoTitle(todoList);
		return service.getCurrNo();
	}
	
	@RequestMapping("insertTodoContent.do")
	public void insertTodoContent(TodoList todoList) {
		service.insertTodoContent(todoList);
	}
	
}
