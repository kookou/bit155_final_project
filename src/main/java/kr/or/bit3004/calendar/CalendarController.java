package kr.or.bit3004.calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;
import kr.or.bit3004.user.SessionUser;

@Controller	
public class CalendarController {

	@Autowired
	private CalendarService service;
	
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("calendar.do")
	public String calendar(HttpSession session,Model model) {
		System.out.println("컨트롤러 타니??");
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		int teamNo = currentUser.getTeamNo();
		
		model.addAttribute("showCalendar",service.showCalendar(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		model.addAttribute("teamNo",teamNo);
		System.out.println(teamNo);
		return "calendar/calendar";
	}
	//모델에 담더닞.. ajax를..
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
}
