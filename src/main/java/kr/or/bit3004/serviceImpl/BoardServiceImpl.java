package kr.or.bit3004.serviceImpl;

import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
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
	public void insertBoard(Board board , HttpServletRequest request) {
		String fileName = board.getFile().getOriginalFilename();
		String path = request.getServletContext().getRealPath("/upload");
		String fpath = path + "\\" + fileName;
		System.out.println(fpath);
		
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(fpath);
			fs.write(board.getFile().getBytes());
			fs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//DB파일명 저장
		board.setFileName(fileName);
		dao.insertBoard(board);
	}
}
