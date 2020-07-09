package kr.or.bit3004.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardComment {
	private int commentNo;
	private String content;
	private String writeDate;
	private String id;
	private int no;
}
