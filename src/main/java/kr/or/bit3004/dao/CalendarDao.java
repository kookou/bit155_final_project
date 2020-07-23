package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.calendar.Calendar;

public interface CalendarDao {
	
	public List<Calendar> showCalendar(int teamNo);
	public void addPlan(Calendar calendar);
	public void updatePlanDrag(Calendar calendar);
	public void updatePlan(Calendar calendar);
	public void deletePlan(Calendar calendar);
	
}
/*
 * @Repository public class CalendarDao {
 * 
 * @Autowired private SqlSession sqlSession;
 * 
 * static final String namespace = "kr.or.bit3004.dao.CalendarDao";
 * 
 * public List<Calendar> showCalendar() throws Exception{ return
 * sqlSession.selectList(namespace+".showCalendar"); }
 * 
 * public void addPlan(Calendar dto)throws Exception{
 * sqlSession.insert(namespace+".addPlan",dto); } }
 */