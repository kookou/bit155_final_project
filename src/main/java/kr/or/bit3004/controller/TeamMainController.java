package kr.or.bit3004.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.service.TeamMainService;

@Controller
public class TeamMainController {

	@Autowired
	private TeamMainService service;
	
	@RequestMapping("teamMain.do")
	public String selectTeamName(String id, Model model) {
		model.addAttribute("groupAndTeam", service.selectGroupAndTeam(id));
		model.addAttribute("group", service.selectGroupName(id));
		return "teamMain/groupAndTeamMain.html";
	}
	
}
