package kr.or.bit3004.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kr.or.bit3004.dao.KanbanDao;
import kr.or.bit3004.dto.Kanban;
import kr.or.bit3004.service.KanbanService;

public class KanbanServiceImpl implements KanbanService {
	
	@Autowired
	private KanbanDao dao;
	
	@Override
	public void insertListTite(Kanban kanban) {
		dao.insertListTite(kanban);
	}
	
	@Override
	public List<Object> allKanbanList(int teamNo){
		return dao.allKanbanList(teamNo);
	}

}
