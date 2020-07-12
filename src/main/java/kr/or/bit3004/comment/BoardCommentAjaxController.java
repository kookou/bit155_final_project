package kr.or.bit3004.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardCommentAjaxController {
	
	@Autowired
	private BoardCommentService boardCommentService;
	
	//댓글목록 가져오기
	@RequestMapping("CommentList.ajax")
	public List<BoardComment> getCommentList(int boardNo){
		return boardCommentService.getCommentList(boardNo);
	}
	
	//댓글등록
	@RequestMapping("InsertComment.ajax")
	public int insertComment(BoardComment boardComment) {
		return boardCommentService.insertComment(boardComment);
	}
}
