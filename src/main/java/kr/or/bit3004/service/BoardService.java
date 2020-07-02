package kr.or.bit3004.service;

import java.util.List;

import kr.or.bit3004.dto.Board;

public interface BoardService {
	public List<Board> selectBoardList(int no);
}
