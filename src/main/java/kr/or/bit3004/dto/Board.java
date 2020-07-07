package kr.or.bit3004.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
	private int no;
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
	private int fileNo;
	private String fileName;
	private String fileSize;
	private String fileOriginName;
	private String fileUrl;
}
