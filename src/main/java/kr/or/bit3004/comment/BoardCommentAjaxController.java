package kr.or.bit3004.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardCommentAjaxController {
	
	@Autowired
	private BoardCommentService boardCommentService;
	
	/*
	* @Method Name : getCommentList
	* @작성일 : 2020.07.28
	* @작성자 : 박혜정, 조진원
	* @Method 설명 : Comment List 가져오기 (ajax)
	* @param boardNo
	* @return Comment List
	**/
	//댓글목록 가져오기
	@RequestMapping("CommentList.ajax")
	public List<BoardComment> getCommentList(int boardNo){
		return boardCommentService.getCommentList(boardNo);
	}
	
	//댓글등록
	@RequestMapping("InsertComment.ajax")
	public List<BoardComment> insertComment(BoardComment boardComment) {
		boardCommentService.insertComment(boardComment);
		return boardCommentService.getCommentList(boardComment.getBoardNo());
	}
	
	//댓글삭제
	@RequestMapping("DeleteComment.ajax")
	public List<BoardComment> deleteComment(BoardComment boardComment) {
		boardCommentService.deleteComment(boardComment);
		return boardCommentService.getCommentList(boardComment.getBoardNo());
	}
	
	//댓글수정
	@RequestMapping("UpdateComment.ajax")
	public List<BoardComment> updateComment(BoardComment boardComment){
		System.out.println(boardComment);
		boardCommentService.updateComment(boardComment);
		return boardCommentService.getCommentList(boardComment.getBoardNo());
	}
}
