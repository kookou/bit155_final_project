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
	public int insertListTite(KanbanList kanbanlist, Principal principal) {
		int newKanbanListNo = 0;
		kanbanlist.setId(principal.getName());
		
		//여기 트랜젝션 처리해야함
		dao.insertListTite(kanbanlist);
		newKanbanListNo = dao.getANewKanbanListNo();
		System.out.println(newKanbanListNo);
		
		return newKanbanListNo;
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
	
	//delete kanban list
	@Override
	public void deleteKanbanList(String listTitle) {
		System.out.println("deleteKanbanList");
		System.out.println("listTitle : " + listTitle);
		dao.deleteKanbanList(listTitle);
		
	}
	
	
	
	/////////////뭔지모름 /////////////////

	@Override
	public List<Map> kanbanList(int teamNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int insertCardTitle(String title , int kanbanListNo) {
		int newcardNo;
		dao.insertCardTitle(title, kanbanListNo);
		newcardNo = dao.getANewCardNo();
		System.out.println(newcardNo);
		
		return newcardNo;
	}
	@Override
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo) {
		return dao.kanbanListFromAllBoardListNo(allBoardListNo);
	}
	
	@Override
	public void updateCardTitle(String title , int cardNo) {
		dao.updateCardTitle(title, cardNo);
	}
	@Override
	public KanbanCard kanbanCardContent(int cardNo) {
		return dao.kanbanCardContent(cardNo);
	}
}
