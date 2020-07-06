package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.dto.Board;

public interface BoardDao {
	//게시판 목록보기
	public List<Board> getBoardList(int no);
	
	//게시판 상세보기
	public Board selectBoardByNo(int boardNo);
	
	//게시판 글쓰기
	public int insertBoard(Board board);
}
