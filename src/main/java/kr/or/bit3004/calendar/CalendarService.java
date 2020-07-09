package kr.or.bit3004.calendar;

import java.util.List;

public interface CalendarService {

	public List<Calendar> showCalendar() throws Exception;
	
	public void addPlan(Calendar dto) throws Exception;
	
	
}
