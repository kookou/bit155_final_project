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

import kr.or.bit3004.service.KanbanService;

@Controller
public class KanbanCoontroller {
	
	@Autowired
	private KanbanService service;
	
	@RequestMapping("/kanban.do")
	public String kanbanList(Model model) {
//		Map<String,Object> kanbanmap = new HashMap<>();
//		Map kanbanmap = new HashMap();
		List<Map> kanbanmapList = new ArrayList<>();
		kanbanmapList = service.allKanbanList();
		System.out.println(kanbanmapList);
		
		
//		model.addAttribute("kanbanList" , );
		return "kanban/kanban";
	}
	
	
}
