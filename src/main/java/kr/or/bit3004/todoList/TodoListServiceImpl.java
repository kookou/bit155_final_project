package kr.or.bit3004.todoList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TodoListDao;

@Service
public class TodoListServiceImpl implements TodoListService {
	
	@Autowired
	private TodoListDao dao;
	
	@Override
	public List<TodoList> selectTodoContent(int teamNo) {
		return dao.getTodoContent(teamNo);
	}
	
	@Override
	public List<TodoList> selectTodoTitle(int teamNo) {
		return dao.getTodoTitle(teamNo);
	}
	
	@Override
	public void insertTodoTitle(TodoList todoList) {
		dao.insertTodoTitle(todoList);
	}
	
	@Override
	public int getCurrNo() {
		return dao.getCurrNo();
	}
	
	@Override
	public void insertTodoContent(TodoList todoList) {
		dao.insertTodoContent(todoList);
	}
}
