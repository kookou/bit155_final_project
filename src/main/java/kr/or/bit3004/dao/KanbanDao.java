package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.kanban.KanbanList;

public interface KanbanDao {
	


	public int insertListTitle(KanbanList kanbanlist);

	public void updateKanbanListTitle(KanbanList kanbanlist);
	
	public List<Map> allKanbanList(int allBoardListNo);

	public int getANewKanbanListNo();
	public int getANewCardNo();
	
	public List<Map> kanbanCardList();

	public List<Map> kanbanListJoinCard(int allBoardListNo);

	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);
	
	//get A KanbanList By KanbanListNo
	public KanbanList getAKanbanListByKanbanListNo(int kanbanListNo);

	public void deleteKanbanList(String listTitle);
	public int insertCardTitle(String title , int kanbanListNo);
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void updateCardTitle(String title , int cardNo);

}
