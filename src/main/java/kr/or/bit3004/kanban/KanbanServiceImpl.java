package kr.or.bit3004.kanban;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.KanbanDao;

@Service
public class KanbanServiceImpl implements KanbanService {
	
	@Autowired
	private KanbanDao dao;
	
	@Override
	public int insertListTite(KanbanList kanbanlist) {
		return dao.insertListTite(kanbanlist);
	}
	@Override
	public int updateListTite(KanbanList kanbanlist) {
		return dao.updateListTite(kanbanlist);
		
	}
	@Override
	public List<Map> allKanbanList(int allBoardListNo){
		return dao.allKanbanList(allBoardListNo);
	}

	
	@Override
	public List<Map> kanbanCardList(){
		return dao.kanbanCardList();
	}
	
	@Override
	public List<Map> kanbanListJoinCard(int allBoardListNo){
		return dao.kanbanListJoinCard(allBoardListNo);
		
	}
	@Override
	public KanbanList kanbanList(KanbanList kanbamlist) {
		return dao.kanbanList(kanbamlist);
	}
}
