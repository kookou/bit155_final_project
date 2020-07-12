package kr.or.bit3004.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	//게시판 목록보기
	@RequestMapping("boardList.do")
	public String selectBoardListService(Model model, int allBoardListNo) {
		model.addAttribute("boardList", service.selectBoardList(allBoardListNo));
		return "board/list";
	}
	
	//게시판 상세보기
	@RequestMapping("selectBoard.do")
	public String selectBoardByBoardNoService(Model model, int boardNo) {
		model.addAttribute("selectBoard", service.selectBoardByBoardNo(boardNo));
		return "board/detail";
	}
	
	//게시판 글쓰기(Form)
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.GET)
	public String insertBoardService() {
		return "board/insert";
	}
	
	//게시판 글쓰기
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.POST)
	public String insertBoardService(Board board){
		service.insertBoard(board);
		//service.fileUploadBoard(board, request);
		return "redirect:boardList.do?allBoardListNo=1";
	}
	
	//게시판 수정하기(Form)
	@RequestMapping(value = "updateBoard.do" , method = RequestMethod.GET)
	public String updateBoardService(int boardNo , Model model) {
		model.addAttribute("board" , service.selectBoardByBoardNo(boardNo));
		return "board/update";
	}
	//게시판 수정하기
	@RequestMapping(value = "updateBoard.do" , method = RequestMethod.POST)
	public String updateBoardService(Board board) {
		service.updateBoard(board);
		return "redirect:boardList.do?allBoardListNo=1";
	}
	
	//게시판 삭제하기
	@RequestMapping("deleteBoard.do")
	public String deleteBoardService(int boardNo) {
		service.deleteBoard(boardNo);
		return "redirect:boardList.do?allBoardListNo=1";
	}
}
 