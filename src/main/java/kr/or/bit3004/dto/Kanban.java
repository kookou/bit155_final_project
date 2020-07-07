package kr.or.bit3004.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Kanban {
	//칸반 보드 리스트 
	private int kanbanListNo;  //공통사항 
	private String listTite;
	private int allBoardListNo;
	private String id;
	
	//칸반 보드 카드
	private int no; //공통사항 
	private String title;
	private String content;
	private int writeDate;
	private int fileCount;
	private int commentCount;
	//private int kanbanListNo;  //공통사항 
	

	
}
