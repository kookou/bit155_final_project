package kr.or.bit3004.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KanbanComment {
	private int commentNo;
	private String content;
	private String writeDate;
	private String id;
	private int cardNo;
	private String nickname;
	private String image;
	
}
