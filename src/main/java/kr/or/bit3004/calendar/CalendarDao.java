package kr.or.bit3004.calendar;

import java.util.List;

public interface CalendarDao {
	
	public List<Calendar> showCalendar();
	public void addPlan(Calendar dto);
	
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