package kr.or.bit3004.cloud;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CloudUpload {
	private int fileNo;
	private String originFileName;
	private String fileName;
	private long fileSize;
	private String filePath;
	private int teamNo;
	private String fileLink;
}
