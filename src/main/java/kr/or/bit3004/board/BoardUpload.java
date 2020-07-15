package kr.or.bit3004.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardUpload {
	private int fileNo;
	private String fileOriginName;
	private String fileName;
	private long fileSize;
	private int allBoardListNo;
	private int boardNo;
	
	
}
