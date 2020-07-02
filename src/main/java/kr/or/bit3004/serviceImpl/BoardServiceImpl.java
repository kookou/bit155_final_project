package kr.or.bit3004.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.BoardDao;
import kr.or.bit3004.dto.Board;
import kr.or.bit3004.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDao dao;
	
	@Override
	public List<Board> selectBoardList(int no){
		return dao.getBoardList(no);
	}
}
