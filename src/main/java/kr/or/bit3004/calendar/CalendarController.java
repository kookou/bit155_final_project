package kr.or.bit3004.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;

@Controller	
public class CalendarController {

	@Autowired
	private CalendarService service;
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("calendar.do")
	public String calendar(int teamNo,Model model) {
		System.out.println("캘린더 타나?????");
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		model.addAttribute("teamNo",teamNo);
		return "calendar/calendar";
	}
	//모델에 담더닞.. ajax를..
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
}
