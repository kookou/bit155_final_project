package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.kanban.KanbanList;

public interface KanbanDao {
	


	public int insertListTitle(KanbanList kanbanlist);

	public int updateListTitle(KanbanList kanbanlist);
	
	public List<Map> allKanbanList(int allBoardListNo);

	public int getANewKanbanListNo();

	
	public List<Map> kanbanCardList();

	public List<Map> kanbanListJoinCard(int allBoardListNo);

	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);


}
