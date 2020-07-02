package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.dto.Board;

public interface BoardDao {
	public List<Board> getBoardList(int no);
}
