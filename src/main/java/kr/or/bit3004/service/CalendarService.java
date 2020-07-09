package kr.or.bit3004.service;

import java.util.List;

import kr.or.bit3004.dto.Calendar;

public interface CalendarService {

	public List<Calendar> showCalendar() throws Exception;
	
	public void addPlan(Calendar dto) throws Exception;
	
	
}
