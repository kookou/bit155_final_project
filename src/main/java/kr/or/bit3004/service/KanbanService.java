package kr.or.bit3004.service;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.or.bit3004.dto.Kanban;

@Service
public interface KanbanService {
	//List title insert
	public void insertListTite(Kanban kanban);
	public List<Map> allKanbanList();
	

}
