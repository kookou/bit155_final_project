package kr.or.bit3004.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TodoListDao;
import kr.or.bit3004.dto.TodoList;
import kr.or.bit3004.service.TodoListService;

@Service
public class TodoListServiceImpl implements TodoListService {
	
	@Autowired
	private TodoListDao dao;
	
	@Override
	public List<TodoList> selectTodoList(int teamNo) {
		return dao.getTodoList(teamNo);
	}
	
	@Override
	public List<TodoList> selectTodoTitle(int teamNo) {
		return dao.getTodoTitle(teamNo);
	}
	
	@Override
	public void insertTodoTitle(TodoList todoList) {
		dao.insertTodoTitle(todoList);
	}
}
