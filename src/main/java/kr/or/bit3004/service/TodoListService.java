package kr.or.bit3004.service;

import java.util.List;

import kr.or.bit3004.dto.TodoList;

public interface TodoListService {
	public List<TodoList> selectTodoList(int teamNo);
	public void insertTodoTitle(TodoList todoList);
}
