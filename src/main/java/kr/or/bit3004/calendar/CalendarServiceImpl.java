package kr.or.bit3004.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImpl implements CalendarService{

	@Autowired
	private CalendarDao dao;
	
	@Override
	public List<Calendar> showCalendar() throws Exception {
		return dao.showCalendar();
	}
	
	@Override
	public void addPlan(Calendar dto) throws Exception {
		dao.addPlan(dto);
	}
}
