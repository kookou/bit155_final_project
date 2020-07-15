package kr.or.bit3004.timeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;

@Controller
public class TimelineController {
	
	@Autowired
	private TimelineService service;
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("timeLine.do")
	public String timeLine(int teamNo, Model model) {
		model.addAttribute("teamNo", teamNo);
		model.addAttribute("timelineList", service.getTimeline(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "timeLine/include";
	}
	
}
