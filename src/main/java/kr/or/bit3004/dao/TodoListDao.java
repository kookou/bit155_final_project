package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.todoList.TodoList;

public interface TodoListDao {
	public List<TodoList> getTodoContent(TodoList todoList);
	public List<TodoList> getTodoTitle(int teamNo);
	public void insertTodoTitle(TodoList todoList);
	public int getCurrNo();
	public void insertTodoContent(TodoList todoList);
	public int getCurrContentNo();
	public void toggleState(TodoList todoList);
	public void deleteTodoContent(int todoContentNo);
	public void deleteTodoTitle(int no);
}
