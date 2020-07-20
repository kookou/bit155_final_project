package kr.or.bit3004.calendar;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarAjaxController {

	@Autowired
	private CalendarService service;


	@RequestMapping("addPlan.ajax")
	public void addPlan(Calendar calendar){
		System.out.println("타니????");
		service.addPlan(calendar);
		
	}

	
	  @RequestMapping("showCalendar.do")
	  public String schedule(Model model)throws Exception{
		  model.addAttribute("showCalendar",service.showCalendar());
		  return "calendar.do";
	  }
	 
		/*
		 * @RequestMapping("addPlan.ajax") public String addPlan(Calendar dto)throws
		 * Exception { service.addPlan(dto); System.out.println(dto.getTitle()); return
		 * "calendar/calendar"; }
		 */

}
