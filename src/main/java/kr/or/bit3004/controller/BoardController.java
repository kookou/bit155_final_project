package kr.or.bit3004.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit3004.dto.Board;
import kr.or.bit3004.service.BoardService;

@Controller
//@RequestMapping
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	//게시판 목록
	@RequestMapping("boardList.do")
	public String selectBoardListService(Model model, int no) {
		model.addAttribute("boardList", service.selectBoardList(no));
		return "board/boardList";
	}
	
	//게시판 상세보기
	@RequestMapping("selectBoard.do")
	public String selectBoardByBoardNoService(Model model, int boardNo) {
		model.addAttribute("selectBoard", service.selectBoardByBoardNo(boardNo));
		return "board/boardDetail";
	}
	
	//게시판 글쓰기(폼)
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.GET)
	public String insertBoard() {
		return "board/insertForm";
	}
	
	//게시판 글쓰기
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.POST)
	public String insertBoard(Board board , HttpServletRequest request){
		service.insertBoard(board, request);
		return "redirect:board/boardList";
	}
	
	//게시판 삭제하기
	@RequestMapping("deleteBoard.do")
	public String deleteBoard(int boardNo) {
		System.out.println(boardNo);
		service.deleteBoard(boardNo);
		return "board/boardList.do";
	}
}
 