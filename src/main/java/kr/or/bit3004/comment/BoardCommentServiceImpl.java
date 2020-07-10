package kr.or.bit3004.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.BoardCommentDao;

@Service
public class BoardCommentServiceImpl implements BoardCommentService{
	@Autowired
	BoardCommentDao boardCommentDao;
	
	//게시판 목록보기
	public List<String> selectCommentList(){
		return boardCommentDao.getCommentList();
	}
	
	/////////////////////여기까지 공통게시판이였습니다///////////////////////////////
}

