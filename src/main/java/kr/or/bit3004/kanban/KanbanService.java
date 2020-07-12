package kr.or.bit3004.kanban;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface KanbanService {
	//List title insert
	public int insertListTitle(KanbanList kanbanlist, Principal principal);

	public List<Map> allKanbanList(int allBoardListNo);
	public List<Map> kanbanCardList();
	public List<Map> kanbanListJoinCard(int allBoardListNo);


	public List<Map> kanbanList(int teamNo);
	
	
	
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);
	
	//update Kanban List
	public KanbanList updateKanbanListTitle(KanbanList kanbanlist, Principal principal);
	
	public int insertCardTitle(String title , int kanbanListNo);
	
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void updateCardTitle(String title , int cardNo);
}
