package kr.or.bit3004.calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
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
		System.out.println("인서트 완료");
		service.addPlan(calendar);
	}
	@RequestMapping("showCalendar.ajax")
	 public List<Calendar> schedule(Model model, int teamNo){
		System.out.println("캘린더 리스트");
		System.out.println(service.showCalendar(teamNo));
		return service.showCalendar(teamNo);
  }
	@RequestMapping("updatePlanDrag.ajax")
	public void updatePlanDrag(Calendar calendar) {
		System.out.println("드래그 업데이트 완료");
		service.updatePlanDrag(calendar);
	}
	@RequestMapping("updatePlan.ajax")
	public void updatePlan(Calendar calendar) {
		System.out.println("업데이트 완료");
		service.updatePlan(calendar);
	}
	 @RequestMapping("deletePlan.ajax")
	 public void deletePlan(Calendar calendar) {
		 System.out.println("삭제완료");
		 service.deletePlan(calendar);
	 }

}
