package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.service.BoardService;

@Controller
//@RequestMapping
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	//board list 출력
	@RequestMapping("boardList.do")
	public String selectBoardListService(Model model, int no) {
		model.addAttribute("boardList", service.selectBoardList(no));
		return "board/boardList";
	}
	
}
 