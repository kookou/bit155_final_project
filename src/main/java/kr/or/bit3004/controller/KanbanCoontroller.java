package kr.or.bit3004.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.bit3004.service.KanbanService;

@Controller
public class KanbanCoontroller {
	
	@Autowired
	private KanbanService service;
	
	@RequestMapping("/kanban.do" )
	public String kanbanList( Model model) {
		List<Map> kanbanlist = new ArrayList<>();
		List<Map> kanbancardlist = new ArrayList<>();
		
//		kanbanlist = service.kanbanList(teamNo);
		kanbancardlist = service.kanbanCardList();
		model.addAttribute("kanbanlist",kanbanlist);
		model.addAttribute("kanbancardlist",kanbancardlist);
		System.out.println(kanbanlist);
		System.out.println(kanbancardlist);
		return "kanban/kanban";
	}
//	
//	@RequestMapping(value = "/kanban.do" , method = RequestMethod.GET )
//	public String kanbanList() {
//		return "kanban/kanban";
//	}
//	
	
//	@RequestMapping(value = "/kanban.do" , method = RequestMethod.POST )
//	public String kanbanList(int teamNo, Model model) {
//		List<Map> kanbanmapList = new ArrayList<>();
////		Map<String,Object> boardname = new HashMap<String, Object>();
////		boardname = service.getGroup(id);
//		kanbanmapList = service.allKanbanList(teamNo);
////		System.out.println(boardname);
//		model.addAttribute("kanbanmapList" , kanbanmapList);
////		model.addAttribute("group" ,boardname );
//		return "kanban/kanban";
//	}
//	
	
}
