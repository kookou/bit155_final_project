package kr.or.bit3004.timeline;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;
import kr.or.bit3004.user.SessionUser;

@Controller
public class TimelineController {
	
	@Autowired
	private TimelineService service;
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("timeLine.do")
	public String timeLine(HttpSession session, int teamNo, Model model) {
		
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		//팀메인 페이지에서 session값(teamNo, teamMember) 리셋
		currentUser.setTeamNo(teamNo);
		currentUser.setIsTeamLeader(
				asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		model.addAttribute("timelineList", service.getTimeline(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "timeline/timeline";
	}
	
}
