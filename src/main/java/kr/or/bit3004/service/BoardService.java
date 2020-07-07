package kr.or.bit3004.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.or.bit3004.dto.Board;

public interface BoardService {
	//게시판 목록보기
	public List<Board> selectBoardList(int no);
	//게시판 상세보기
	public Board selectBoardByBoardNo(int boardNo);
	//게시판 글쓰기
	public int insertBoard(Board board , HttpServletRequest request);
	//게시판 수정하기
	public void updateBoard(Board board , HttpServletRequest request);
	//게시판 삭제하기
	public void deleteBoard(int boardNo);
}
