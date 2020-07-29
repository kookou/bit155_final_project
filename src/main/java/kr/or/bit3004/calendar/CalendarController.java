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
	
	
	/**
	 * @Method Name : calendar
	 * @작성일 : 2020.07.28
	 * @작성자 : 박혜정
	 * @Method 설명 : calendar 초기 로딩시 동기로 보여줄 일정 내용 셀렉트
	 * @param : session
	 * @param : model
	 * @return : calendar/calendar
	 **/
	
	@RequestMapping("calendar.do")
	public String calendar(HttpSession session,Model model) {
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		int teamNo = currentUser.getTeamNo();
		
		model.addAttribute("showCalendar",service.showCalendar(teamNo));
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		model.addAttribute("teamNo",teamNo);
		
//		System.out.println("캘린더" +service.showCalendar(teamNo));
		return "calendar/calendar";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
}
