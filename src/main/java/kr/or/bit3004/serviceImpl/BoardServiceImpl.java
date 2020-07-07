package kr.or.bit3004.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.BoardDao;
import kr.or.bit3004.dto.Board;
import kr.or.bit3004.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDao dao;

	//게시판 목록보기 
	@Override
	public List<Board> selectBoardList(int no){
		return dao.getBoardList(no);
	}

	//게시판 상세보기
	@Override
	public Board selectBoardByBoardNo(int boardNo) {
		return dao.selectBoardByNo(boardNo);
	}
	
	//게시판 글쓰기
	@Override
	public int insertBoard(Board board , HttpServletRequest request) {
		return dao.insertBoard(board);
	}
	
	//게시판 삭제하기
	@Override
	public void deleteBoard(int boardNo) {
		dao.deleteBoard(boardNo);
	}
	
}
