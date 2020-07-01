package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.service.TestService;

@Controller
public class BoardController {
	
	@Autowired
	private TestService service;
	
	@RequestMapping("/insertForm.do")
	public String boardInsert() {
		return "board/insertForm";
	}

	@RequestMapping("/boardList.do")
	public String boardList() {
		return "board/boardList";
	}
}
