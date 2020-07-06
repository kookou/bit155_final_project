package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.dto.TodoList;

public interface TodoListDao {
	public List<TodoList> getTodoList(int teamNo);
	public void insertTodoTitle(TodoList todoList);
}
