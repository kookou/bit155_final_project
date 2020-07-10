package kr.or.bit3004.timeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimelineController {
	
	@Autowired
	private TimelineService service;
	
	@RequestMapping("timeLine.do")
	public String timeLine(int teamNo, Model model) {
		model.addAttribute("timelineList", service.getTimeline(teamNo));
		model.addAttribute("team", service.getTeam(teamNo));
		model.addAttribute("teamMember", service.getTeamMember(teamNo));
		return "timeLine/include";
	}
	
}
