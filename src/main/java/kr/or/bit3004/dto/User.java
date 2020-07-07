package kr.or.bit3004.dto;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile file; //파일 첨부를 위한 file
	
	private int enable;
	private String quit;
		
}
