package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.kanban.KanbanList;

public interface KanbanDao {
	
	public int insertListTite(KanbanList kanbanlist);
	
	public List<Map> allKanbanList(int teamNo);
	public Map<String,Object> getGroup(String id);
	public List<Map> kanbanCardList();
	public List<Map> kanbanList(int teamNo);

}
