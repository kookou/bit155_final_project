package kr.or.bit3004.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller	
public class CalendarController {

	@Autowired
	private CalendarService service;
	
	@RequestMapping("calendar.do")
	public String calendar(int teamNo,Model model) {
		
		model.addAttribute("teamNo",teamNo);
		return "calendar/calendar";
		//return "../calendarTest/index";
	}
	//모델에 담더닞.. ajax를..
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
}
