package kr.or.bit3004.todoList;

import java.util.List;

public interface TodoListService {
	public List<TodoList> selectTodoList(TodoList todoList);
	public List<TodoList> selectTodoTitle(int teamNo);
	public void insertTodoTitle(TodoList todoList);
}
