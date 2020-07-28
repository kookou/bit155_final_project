package kr.or.bit3004.kanban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import kr.or.bit3004.aside.AsideService;
import kr.or.bit3004.user.SessionUser;

@Controller
public class KanbanController {
	
	@Autowired
	private KanbanService service;
	
	@Autowired
	private AsideService asideService;
	
	/**
    * @Method Name : kanbanList
    * @작성일 : 2020.07.28
    * @작성자 : 박혜정
    * @Method 설명 : kanban 게시판 초기 로딩시 필요한 동기 데이터
    * @param : session
    * @param : allBoardListNo
    * @param : model
    * @return : kanban/kanban
    **/
	
	@RequestMapping("/kanban.do")
	public String kanbanList(HttpSession session, int allBoardListNo, Model model) {
		
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		int teamNo = currentUser.getTeamNo();		
		Map<String, String> boardnamelist = new HashMap<String, String>();
		List<KanbanCard> kanbancardlist = new ArrayList<>();
		List<KanbanList> kanbanlist = new ArrayList<>();

		kanbanlist = service.kanbanListFromAllBoardListNo(allBoardListNo);
		kanbancardlist = service.kanbanCardList();
		boardnamelist = service.boardNameSelect(allBoardListNo);
		
		model.addAttribute("kanbanlist",kanbanlist);
		model.addAttribute("kanbancardlist",kanbancardlist);
		model.addAttribute("boardnamelist" , boardnamelist);
		
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		
		return "kanban/kanban";
	}


}
 