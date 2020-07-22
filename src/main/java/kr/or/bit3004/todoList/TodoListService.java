package kr.or.bit3004.todoList;

import java.util.List;

public interface TodoListService {
	public List<TodoList> selectTodoContent(int teamNo);
	public List<TodoList> selectTodoTitle(int teamNo);
	public void insertTodoTitle(TodoList todoList);
	public int getCurrNo();
	public void insertTodoContent(TodoList todoList);
	public int getCurrContentNo();
	public void toggleState(TodoList todoList);
	public void deleteTodoContent(int todoContentNo);
	public void deleteTodoTitle(int no);
}
