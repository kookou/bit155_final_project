package kr.or.bit3004.kanban;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KanbanAjaxController {
	
	@Autowired
	private KanbanService service;
	
//	@RequestMapping("/kanban.do")
//	public String kanbanList(int teamNo, Model model) {
//		List<Map> kanbanlist = new ArrayList<>();
//		List<Map> kanbancardlist = new ArrayList<>();
//		
//		kanbanlist = service.kanbanList(teamNo);
//		kanbancardlist = service.kanbanCardList();
//		model.addAttribute("kanbanlist",kanbanlist);
//		model.addAttribute("kanbancardlist",kanbancardlist);
//		System.out.println(kanbanlist);
//		System.out.println(kanbancardlist);
//		return "kanban/kanban";
//	}
	@RequestMapping("InsertKanbanList.ajax")
	public String kanbanListInsert(KanbanList kanbanlist, Principal principal) {
		System.out.println("Controller kanbanListInsert");
		
		System.out.println(kanbanlist);
		
		int newKanbanListNo = service.insertListTite(kanbanlist, principal);
		String newKanbanListNoString = Integer.toString(newKanbanListNo);
		
		
		return newKanbanListNoString;
	}
	
	@RequestMapping("deleteKanbanList.ajax")
	public String deleteKanbanList(KanbanList kanbanlist) {
		System.out.println("Controller deleteKanbanList");
		
		service.deleteKanbanList(kanbanlist.getListTitle());
		return "redirect:kanban.do?teamNo=1&allBoardListNo=2";
	}
	
}
 