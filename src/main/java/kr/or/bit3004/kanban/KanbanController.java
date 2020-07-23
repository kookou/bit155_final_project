package kr.or.bit3004.kanban;

import java.util.ArrayList;
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
	
	@RequestMapping("/kanban.do")
	public String kanbanList(HttpSession session, int allBoardListNo, Model model) {
		
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		int teamNo = currentUser.getTeamNo();		
		
		List<KanbanCard> kanbancardlist = new ArrayList<>();
		List<KanbanList> kanbanlist = new ArrayList<>();

		kanbanlist = service.kanbanListFromAllBoardListNo(allBoardListNo);
		kanbancardlist = service.kanbanCardList();
		
		model.addAttribute("kanbanlist",kanbanlist);
		model.addAttribute("kanbancardlist",kanbancardlist);

		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		
		System.out.println(kanbanlist);
		System.out.println(kanbancardlist);
		return "kanban/kanban";
	}


}
 