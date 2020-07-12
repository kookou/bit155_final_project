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
	public List<Map> allKanbanList(int teamNo){
		return dao.allKanbanList(teamNo);
	}
	
	@Override
	public Map<String,Object> getGroup(String id){
		return dao.getGroup(id);
	}
	@Override
	public List<Map> kanbanCardList(){
		return dao.kanbanCardList();
	}
	@Override
	public List<Map> kanbanList(int teamNo){
		return dao.kanbanList(teamNo);
		
	}
	
	
	//delete kanban list
	@Override
	public void deleteKanbanList(String listTitle) {
		System.out.println("deleteKanbanList");
		System.out.println("listTitle : " + listTitle);
		dao.deleteKanbanList(listTitle);
	}
}
