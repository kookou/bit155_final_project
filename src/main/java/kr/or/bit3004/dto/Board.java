package kr.or.bit3004.dto;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
	private int boardNo;
	private String title;
	private String content;
	private int views;
	private String writeDate;
	private int commentCount;
	private int refer;
	private int depth;
	private int step;
	private int allBoardListNo;
	private String id;
	private String nickname;
	private String name;
	
	//BOARD_FILE 테이블 변수명
	private List<CommonsMultipartFile> files;
	private int fileNo;
	private String fileName;
	private int fileSize;
	
}
