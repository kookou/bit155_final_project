package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.dto.Board;

public interface BoardDao {
	//게시판 목록보기
	public List<Board> getBoardList(int allBoardListNo);
	
	//게시판 상세보기
	public Board selectBoardByNo(int no);
	
	//게시판 글쓰기
	public int insertBoard(Board board);
	
	//게시판 수정하기
	public void updateBoard(Board board);
	
	//게시판 삭제하기
	public int deleteBoard(int no);
	
	//게시판 파일 업로드
	public List<Board> getFileBoardList();
	
	//파일 업로드 메소드 추가
	public int fileUpload(Board board);
	
}
