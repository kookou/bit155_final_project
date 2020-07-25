package kr.or.bit3004.board;

import java.util.Date;

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
	private Date writeDate;
	private int commentCount;
	private int refer;
	private int depth;
	private int step;
	private int allBoardListNo;
	private String id;
	private String nickname;
	private String name;
	private String fileCount;
	private String boardNoti;
}
