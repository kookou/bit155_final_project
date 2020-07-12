package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.comment.BoardComment;

public interface BoardCommentDao {
	
	//댓글목록보기
	public List<BoardComment> getCommentList(int boardNo);
	
	///////////////////////////보드//////////////////////////////
}
