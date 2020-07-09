package kr.or.bit3004.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit3004.service.BoardCommentService;

@RestController
public class BoardCommentAjaxController {
	@Autowired
	private BoardCommentService boardCommentService;
	
	//댓글목록 가져오기
	@RequestMapping("CommentList.ajax")
	public List<String> selectCommentList(int no){
		System.out.println(no);
		return boardCommentService.selectCommentList();
	}
}
