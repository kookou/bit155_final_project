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
	public List<Board> selectBoardList(int allBoardListNo){
		return dao.getBoardList(allBoardListNo);
	}

	//게시판 상세보기
	@Override
	public Board selectBoardByBoardNo(int no) {
		return dao.selectBoardByNo(no);
	}
	
	//게시판 글쓰기
	@Override
	public int insertBoard(Board board , HttpServletRequest request) {
		return dao.insertBoard(board);
	}
	
	//게시판 수정하기
	@Override
	public void updateBoard(Board board , HttpServletRequest request) {
		dao.updateBoard(board);
	}
	
	//게시판 삭제하기
	@Override
	public void deleteBoard(int no) {
		dao.deleteBoard(no);
	}
	
}
