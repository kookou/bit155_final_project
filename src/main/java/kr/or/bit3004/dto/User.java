package kr.or.bit3004.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String id;
	private String pwd;
	private String nickname;
	private String image;
	private int enable;
	private String quit;
	
}
