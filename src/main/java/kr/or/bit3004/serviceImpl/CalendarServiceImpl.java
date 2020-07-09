package kr.or.bit3004.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.CalendarDao;
import kr.or.bit3004.dto.Calendar;
import kr.or.bit3004.service.CalendarService;

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
