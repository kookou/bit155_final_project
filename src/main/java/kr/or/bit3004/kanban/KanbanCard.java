package kr.or.bit3004.kanban;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KanbanCard {
	
	//칸반 보드 카드
	private int cardNo; //공통사항 
	private String id; // 작성자 
	private String title; 
	private String content; //내용 
	private String writeDate; //작성일 
	private int fileCount; //파일 개수 
	private int commentCount;  //댓글 개수 
	private int cardIndex;
	private int kanbanListNo;  //공통사항 
}
