package kr.or.bit3004.kanban;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KanbanUpload {
	private int fileNo;
	private String fileName;
	private int fileSize;
	private int allBoardListNo;
	private int cardNo;
	
//	private String fileOriginName;
//	private String fileUrl;
}
