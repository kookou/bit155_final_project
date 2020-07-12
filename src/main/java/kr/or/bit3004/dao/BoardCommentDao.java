package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.comment.BoardComment;

public interface BoardCommentDao {
	
	//댓글 목록보기
	public List<BoardComment> getCommentList(int boardNo);
	
	//댓글 등록하기
	public int insertComment(BoardComment boardComment);
	///////////////////////////보드//////////////////////////////
}
