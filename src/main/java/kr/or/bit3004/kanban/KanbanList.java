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
	private String listTite; 
	private int allBoardListNo; //게시판식별번호
	private String id;
//	
//	//칸반 보드 카드
//	private int no; //공통사항 
//	private String title; 
//	private String content; //내용 
//	private int writeDate; //작성일 
//	private int fileCount; //파일 개수 
//	private int commentCount;  //댓글 개수 
//	//private int kanbanListNo;  //공통사항 
	

	
}
