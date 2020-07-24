package kr.or.bit3004.timeline;

import java.util.HashMap;
import java.util.Map;

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
		
		Map<String, String> chartInfo = new HashMap<String, String>();
		
		chartInfo.put("totalTodos", Integer.toString(service.countTotalTodos(teamNo)));
		chartInfo.put("totalPosts", Integer.toString(service.countTotalPosts(teamNo)));
		chartInfo.put("totalFiles", Integer.toString(service.countTotalUploadFiles(teamNo)));
//		chartInfo.put("activeRate", value);
//		chartInfo.put("progress", value);
//		chartInfo.put("todaysNew", value);
		
		model.addAttribute("timelineList", service.getTimeline(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		
		model.addAttribute("chartInfo", chartInfo);
		return "timeline/timeline";
	}
	
}
