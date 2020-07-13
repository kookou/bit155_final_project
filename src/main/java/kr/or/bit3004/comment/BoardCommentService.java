package kr.or.bit3004.comment;

import java.util.List;

public interface BoardCommentService {
	//댓글 목록보기
	public List<BoardComment> getCommentList(int boardNo);
	
	//댓글 등록하기
	public void insertComment(BoardComment boardComment);
	
	//댓글 삭제하기
	public void deleteComment(BoardComment boardComment);
	
	//댓글 수정하기
	public void updateComment(BoardComment boardComment);
	/////////////////////여기까지 공통게시판이였습니다///////////////////////////////
}
