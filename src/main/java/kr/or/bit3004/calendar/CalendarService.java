package kr.or.bit3004.calendar;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;

public interface CalendarService {

	public List<Calendar>  showCalendar(int teamNo);
	
	public void addPlan(Calendar calendar);
	
	public void updatePlanDrag(Calendar calendar);
	
	public void updatePlan(Calendar calendar);
	
	public void deletePlan(Calendar calendar);
	
	
}
