package kr.or.bit3004.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit3004.aside.AsideService;

@Controller
//@RequestMapping
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private AsideService asideService;
	
	//게시판 목록보기
	@RequestMapping("boardList.do")
	public String selectBoardListService(Model model, int allBoardListNo, int teamNo) {
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("boardList", service.selectBoardList(allBoardListNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		System.out.println();
		return "board/list";
	}
	
	//게시판 상세보기
	@RequestMapping("selectBoard.do")
	public String selectBoardByBoardNoService(Model model, int boardNo, int teamNo,int allBoardListNo,int refer) {
		service.updateReadCount(boardNo);
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("refer",refer);
		model.addAttribute("selectBoard", service.selectBoardByBoardNo(boardNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/detail";
	}
	
	//게시판 글쓰기(Form)
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.GET)
	public String insertBoardService(Model model, int teamNo, int allBoardListNo) {
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/insert";
	}
	
	//게시판 글쓰기
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.POST)
	public String insertBoardService(Board board , int teamNo){
		System.out.println(board);
		service.insertBoard(board);
		return "redirect:boardList.do?allBoardListNo="+board.getAllBoardListNo()+"&teamNo="+teamNo;
	}
	
	//파일 업로드
	/*
	@RequestMapping("insertBoard.do")
	public List<String> boardFilesUpload(MultipartHttpServletRequest request){
		return service.boardFilesUpload(request);
	}
	*/
	//게시판 답글쓰기(Form)
	@RequestMapping(value = "insertReboard.do" , method = RequestMethod.GET)
	public String insertReboardService(Model model,int boardNo, int teamNo,int allBoardListNo) {
		/*
		 * System.out.println("보드넘"+boardNo); model.addAttribute("boardNo", boardNo);
		 * //삭제각
		 */		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("board" , service.selectBoardByBoardNo(boardNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/reinsert";
	}
	
	//게시판 답글쓰기
	@RequestMapping(value = "insertReboard.do" , method = RequestMethod.POST)
	public String insertReboardService(Board board, int teamNo,int boardNo){
		System.out.println(board);
		service.insertReboard(board);
		return "redirect:boardList.do?allBoardListNo="+board.getAllBoardListNo()+"&teamNo="+teamNo;
	}
	
	//게시판 수정하기(Form)
	@RequestMapping(value = "updateBoard.do" , method = RequestMethod.GET)
	public String updateBoardService(Model model, int boardNo, int teamNo) {
		model.addAttribute("board" , service.selectBoardByBoardNo(boardNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/update";
	}
	
	//게시판 수정하기
	@RequestMapping(value = "updateBoard.do" , method = RequestMethod.POST)
	public String updateBoardService(Board board) {
		service.updateBoard(board);
		return "redirect:boardList.do?allBoardListNo=1&teamNo=1";
	}
	
	//게시판 삭제하기
	@RequestMapping("deleteBoard.do")
	public String deleteBoardService(int boardNo) {
		service.deleteBoard(boardNo);
		return "redirect:boardList.do?allBoardListNo=1&teamNo=1";
	}
	
}
 