package kr.or.bit3004.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class BoardUpload {
	private int fileNo;
	private String fileName;
	private String fileSize;
	private String fileOriginName;
	private String fileUrl;
	
}
