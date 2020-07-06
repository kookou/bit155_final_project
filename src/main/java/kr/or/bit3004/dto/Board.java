package kr.or.bit3004.dto;

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
	private int no;
	private String id;
	private String nickname;
	private String name;
	private CommonsMultipartFile file;
	private String fileName;
}
