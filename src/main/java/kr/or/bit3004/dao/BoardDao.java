package kr.or.bit3004.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.bit3004.board.Board;

@Repository
public interface BoardDao {
	//게시판 목록보기
	public List<Board> getBoardList(int allBoardListNo);
	
	//게시판 상세보기
	public Board selectBoardByNo(int boardNo);
	
	//게시판 조회수 증가
	public void updateReadCount(int boardNo);
	
	//게시판 글쓰기
	public void insertBoard(Board board);
	
	//파일 업로드
	//public int fileUpload(Board board);
	
	//게시판 수정하기
	public void updateBoard(Board board);
	
	
	//게시판 삭제하기
	public int deleteBoard(int boardNo);
	
	//게시판 답글쓰기
	public void insertReboard(Board board);
	
	public void updateRefer(Board board);
	
	public void updateReboardAddstep(Board board);
	
	public int getMaxRefer();
	
	public Board getReferDepthStep(int boardNo);
	
	public int getStep (Board board);
	
	public int getMaxStep (Board board);
	
	public void updateStep(Board board);
	
}
