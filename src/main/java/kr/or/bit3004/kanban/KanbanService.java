package kr.or.bit3004.kanban;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface KanbanService {
	//List title insert
	public int insertListTite(KanbanList kanbanlist);
	public int updateListTite(KanbanList kanbanlist);
	
	public List<Map> allKanbanList(int allBoardListNo);

	public List<Map> kanbanCardList();
	public List<Map> kanbanList(int allBoardListNo);
	
}
