package kr.or.bit3004.kanban;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;

@Controller
public class KanbanController {
	
	@Autowired
	private KanbanService service;
	
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("/kanban.do")
	public String kanbanList(int teamNo, Model model) {
		List<Map> kanbanlist = new ArrayList<>();
		List<Map> kanbancardlist = new ArrayList<>();
		
		kanbanlist = service.kanbanList(teamNo);
		kanbancardlist = service.kanbanCardList();
		model.addAttribute("kanbanlist",kanbanlist);
		model.addAttribute("kanbancardlist",kanbancardlist);
	   model.addAttribute("team", asideService.getTeam(teamNo));
	   model.addAttribute("teamMember", asideService.getTeamMember(teamNo));

		System.out.println(kanbanlist);
		System.out.println(kanbancardlist);
		return "kanban/kanban";
	}
//	@RequestMapping("InsertKanbanList.ajax")
//	public String kanbanListInsert(KanbanList kanbanlist) {
//		System.out.println("너니???");
//		service.insertListTite(kanbanlist);
//		return "redirect:kanban.do?teamNo=1";
//	}
}
 