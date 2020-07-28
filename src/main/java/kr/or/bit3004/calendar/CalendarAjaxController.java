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

	/**
	 * @Method Name : addPlan
	 * @작성일 : 2020.07.28
	 * @작성자 : 박혜정
	 * @Method 설명 : calendar 일정 인서트 인서트. 비동기 처리를 위해 시퀀스로 부여된 일정 번호를 다시 리턴 해줌
	 * @param : calendar 객체
	 * @return : 일정 번호
	 **/
	@RequestMapping("addPlan.ajax")
	public int addPlan(Calendar calendar){
		service.addPlan(calendar);
		return service.getLastNo();
	}
	
	//화면에 뿌릴 캘린더 일정 내용
	@RequestMapping("showCalendar.ajax")
	 public List<Calendar> schedule(Model model, int teamNo){
		return service.showCalendar(teamNo);
	}
	
	//드래그앤 드롭,리사이즈시 일정 업데이트
	@RequestMapping("updatePlanDrag.ajax")
	public void updatePlanDrag(Calendar calendar) {
		service.updatePlanDrag(calendar);
	}
	
	//모달창으로 일정 업데이트
	@RequestMapping("updatePlan.ajax")
	public void updatePlan(Calendar calendar) {
		service.updatePlan(calendar);
	}
	
	//모달 창으로 일정 삭제
	 @RequestMapping("deletePlan.ajax")
	 public void deletePlan(Calendar calendar) {
		 service.deletePlan(calendar);
	 }

}
