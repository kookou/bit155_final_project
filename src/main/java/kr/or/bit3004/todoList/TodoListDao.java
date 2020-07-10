package kr.or.bit3004.todoList;

import java.util.List;

public interface TodoListDao {
	public List<TodoList> getTodoList(int teamNo);
	public List<TodoList> getTodoTitle(int teamNo);
	public void insertTodoTitle(TodoList todoList);
}
