package kr.or.bit3004.cloud;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface CloudService {
	//파일 목록보기
	public List<CloudUpload> getFileList(int teamNo);
	
	//파일 등록하기
	public List<CloudUpload> uploadFile(MultipartHttpServletRequest request);
	
	//댓글 삭제하기
	//public List<CloudUpload> deleteFile(int teamNo,String delFile);
	public void deleteFile(int teamNo,String delFile);
}
