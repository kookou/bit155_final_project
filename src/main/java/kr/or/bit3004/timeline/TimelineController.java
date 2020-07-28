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
	
	
	
	/*
    * @Method Name : timeLine (타임라인 폼 이동)
    * @작성자 : 김혜린, 김선(chart)
    * @변경이력 :
    * @Method 설명 : session을 통해 로그인한 사용자의 정보를 가져와서 teamNo와 리더여부를 세션 사용자 값에 저장, 
    * 			   teamNo로 해당 팀 정보를 가져와서 팀 정보, 차트정보를 model에 담음 
    * @param : HttpSession session
    * @param : int teamNo
    * @param : Model model
    * @return : String
    */
	@RequestMapping("timeLine.do")
	public String timeLine(HttpSession session, int teamNo, Model model) {
		
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		currentUser.setTeamNo(teamNo);
		currentUser.setIsTeamLeader(
				asideService.isTeamLeader(currentUser.getId(), teamNo));
		
		Map<String, String> chartInfo = new HashMap<String, String>();
		
		chartInfo.put("totalTodos", Integer.toString(service.countTotalTodos(teamNo)));
		chartInfo.put("totalPosts", Integer.toString(service.countTotalPosts(teamNo)));
		chartInfo.put("totalFiles", Integer.toString(service.countTotalUploadFiles(teamNo)));
		chartInfo.put("activeRate", service.getActiveRate(teamNo));
		chartInfo.put("progress", Integer.toString(service.getProgress(teamNo)));
		chartInfo.put("todaysNew", service.getTodaysNewPosts(teamNo));
		
		model.addAttribute("timelineList", service.getTimeline(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		
		model.addAttribute("chartInfo", chartInfo);
		
		return "timeline/timeline";
	}
	
}
