package kr.or.bit3004.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.KanbanDao;
import kr.or.bit3004.dto.Kanban;
import kr.or.bit3004.service.KanbanService;

@Service
public class KanbanServiceImpl implements KanbanService {
	
	@Autowired
	private KanbanDao dao;
	
	@Override
	public void insertListTite(Kanban kanban) {
		dao.insertListTite(kanban);
	}
	
	@Override
	public List<Map> allKanbanList(){
		return dao.allKanbanList();
	}

}
