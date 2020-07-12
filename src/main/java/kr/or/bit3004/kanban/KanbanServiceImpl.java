package kr.or.bit3004.kanban;

import java.security.Principal;
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
	public int insertListTitle(KanbanList kanbanlist, Principal principal) {
		int newKanbanListNo = 0;
		kanbanlist.setId(principal.getName());
		
		//여기 트랜젝션 처리해야함
		dao.insertListTitle(kanbanlist);
		newKanbanListNo = dao.getANewKanbanListNo();
		System.out.println(newKanbanListNo);
		
		return newKanbanListNo;
	}
	
	
	@Override
	public KanbanList updateKanbanListTitle(KanbanList kanbanlist, Principal principal) {
		kanbanlist.setId(principal.getName());
		
		dao.updateKanbanListTitle(kanbanlist);
		KanbanList newKanbanList = dao.getAKanbanListByKanbanListNo(kanbanlist.getKanbanListNo());
		System.out.println("newKanbanList:"+newKanbanList);
		return newKanbanList;
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
	
	//delete kanban list
	@Override
	public void deleteKanbanList(int kanbanListNo) {
		System.out.println("deleteKanbanList");
		System.out.println("kanbanListNo : " + kanbanListNo);
		dao.deleteKanbanList(kanbanListNo);
		
	}
	
	
	
	/////////////뭔지모름 /////////////////

	@Override
	public List<Map> kanbanList(int teamNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
