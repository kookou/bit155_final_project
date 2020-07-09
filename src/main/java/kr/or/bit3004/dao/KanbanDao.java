package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.dto.Kanban;

public interface KanbanDao {
	
	public void insertListTite(Kanban kanban);
	public List<Map> allKanbanList();
	

}
