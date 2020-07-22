package kr.or.bit3004.board;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;

import kr.or.bit3004.aside.AsideService;
import kr.or.bit3004.user.SessionUser;

@Controller
//@RequestMapping
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private AsideService asideService;
	
	//게시판 목록보기
	@RequestMapping("boardList.do")
	public String selectBoardListService(Model model, int allBoardListNo, int teamNo, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("boardList", service.selectBoardList(allBoardListNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/list";
	}
	
	//게시판 상세보기
	@RequestMapping("selectBoard.do")
	public String selectBoardByBoardNoService(Model model, int boardNo, int teamNo, int allBoardListNo, String id, HttpServletRequest request, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("selectBoardDownloadFile", service.selectBoardDownloadFile(boardNo)); //다운로드 서비스
		service.updateReadCount(boardNo, request);
		model.addAttribute("selectBoard", service.selectBoardByBoardNo(boardNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/detail";
	}
	
	//게시판 글쓰기(Form)
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.GET)
	public String insertBoardService(Model model, int teamNo, int allBoardListNo, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/insert";
	}
	
	//게시판 글쓰기, 업로드
	@RequestMapping(value = "insertBoard.do" , method = RequestMethod.POST)
	public String insertBoardService(Board board , int teamNo , MultipartHttpServletRequest request, HttpSession session){
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
	    
		System.out.println("controller");
		service.insertBoard(board);
		service.insertBoardUploadFile(request);
		return "redirect:boardList.do?allBoardListNo="+board.getAllBoardListNo()+"&teamNo="+teamNo;
	}
	
	//게시판 답글쓰기(Form)
	@RequestMapping(value = "insertReboard.do" , method = RequestMethod.GET)
	//public String insertReboardService(Model model,int boardNo, int teamNo,int allBoardListNo) {
	public String insertReboardService(Model model, int teamNo, int allBoardListNo, int boardNo, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("allBoardListNo", allBoardListNo);
		model.addAttribute("board" , service.selectBoardByBoardNo(boardNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "board/reinsert";
	}
	
	//게시판 답글쓰기
	@RequestMapping(value = "insertReboard.do" , method = RequestMethod.POST)
	public String insertReboardService(Board board, int teamNo, HttpSession session){
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		System.out.println(board);
		service.insertReboard(board);
		return "redirect:boardList.do?allBoardListNo="+board.getAllBoardListNo()+"&teamNo="+teamNo;
	}
	
	//게시판 수정하기(Form)
	@RequestMapping(value = "updateBoard.do" , method = RequestMethod.GET)
	public String updateBoardService(Model model, int boardNo, int teamNo, int allBoardListNo, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		model.addAttribute("board" , service.selectBoardByBoardNo(boardNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		model.addAttribute("selectBoardDownloadFile", service.selectBoardDownloadFile(boardNo)); //다운로드 서비스
		model.addAttribute("allBoardListNo",allBoardListNo);
		model.addAttribute("teamNo", teamNo);
		return "board/update";
	}
	
	//게시판 수정하기
	@RequestMapping(value = "updateBoard.do" , method = RequestMethod.POST)
	public String updateBoardService(Board board, int allBoardListNo, int teamNo, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
	    
		service.updateBoard(board);
		return "redirect:boardList.do?allBoardListNo="+allBoardListNo+"&teamNo="+teamNo; 
	}
	
	//게시판 삭제하기
	@RequestMapping("deleteBoard.do")
	public String deleteBoardService(int boardNo, int allBoardListNo, int teamNo, HttpSession session) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	    //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
        currentUser.setTeamNo(teamNo);
	    currentUser.setIsTeamLeader(
		asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		service.deleteBoard(boardNo);
		return "redirect:boardList.do?allBoardListNo="+allBoardListNo+"&teamNo="+teamNo; 
	}
	
	//썸머노트 이미지 업로드
	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		JsonObject jsonObject = new JsonObject();
		
		String fileRoot = "C:\\summernote_image\\"; //저장될 외부 파일 경로
		String originalFileName = multipartFile.getOriginalFilename(); //오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자
		String savedFileName = UUID.randomUUID() + extension; //저장될 파일명
		
		File targetFile = new File(fileRoot + savedFileName);
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
			jsonObject.addProperty("responseCode", "success");
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		return jsonObject;
	}
}
 