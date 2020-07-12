package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.kanban.KanbanList;

public interface KanbanDao {
	
	public int insertListTite(KanbanList kanbanlist);

	public int updateListTite(KanbanList kanbanlist);
	
	public List<Map> allKanbanList(int allBoardListNo);


	public int getANewKanbanListNo();
	public int getANewCardNo();
	
	public List<Map> kanbanCardList();

	public List<Map> kanbanListJoinCard(int allBoardListNo);

//	public KanbanList kanbanList(KanbanList kanbamlist);

	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(String listTitle);
	public int insertCardTitle(String title , int kanbanListNo);
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void updateCardTitle(String title , int cardNo);

}
