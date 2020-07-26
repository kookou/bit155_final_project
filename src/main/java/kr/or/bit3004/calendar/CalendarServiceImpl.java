package kr.or.bit3004.calendar;

import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.CalendarDao;

@Service
public class CalendarServiceImpl implements CalendarService{

	@Autowired
	private CalendarDao dao;
	
	@Override
	public List<Calendar>  showCalendar(int teamNo){
		return dao.showCalendar(teamNo);
	}
	
	@Override
	public void addPlan(Calendar calendar){
		dao.addPlan(calendar);
	}
	@Override
	public void updatePlanDrag(Calendar calendar) {
		dao.updatePlanDrag(calendar);
	}
	@Override
	public void updatePlan(Calendar calendar) {
		dao.updatePlan(calendar);
	}
	@Override
	public void deletePlan(Calendar calendar) {
		dao.deletePlan(calendar);
	}
	@Override
	public int getLastNo() {
		return dao.getLastNo();
	}
}
