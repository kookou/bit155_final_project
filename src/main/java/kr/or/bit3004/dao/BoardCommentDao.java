package kr.or.bit3004.dao;

import java.util.List;

public interface BoardCommentDao {
	
	//댓글목록보기
	public List<String> getCommentList(int boardNo);
	
	///////////////////////////보드//////////////////////////////
}
