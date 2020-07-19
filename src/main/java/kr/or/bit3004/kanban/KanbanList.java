package kr.or.bit3004.kanban;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KanbanList {
	//칸반 보드 리스트 
	private int kanbanListNo; //칸반리스트식별번호   //공통사항
	private int kanbanListIndex;
	private String listTitle; 
	private int allBoardListNo; //게시판식별번호
	private String id;


	
}
