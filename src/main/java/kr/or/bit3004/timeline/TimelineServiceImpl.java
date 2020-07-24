package kr.or.bit3004.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TimelineDao;

@Service
public class TimelineServiceImpl implements TimelineService {
	
	@Autowired
	private TimelineDao dao;
	
	@Override
	public List<Timeline> getTimeline(int teamNo) {
		return dao.getTimeline(teamNo);
	}
	
	@Override
	public int countTotalTodos(int teamNo) {
		return dao.countTotalTodos(teamNo);
	}
	
	@Override
	public int countTotalPosts(int teamNo) {		
		int result = dao.countTotalKanbanCards(teamNo)
				   + dao.countTotalBoards(teamNo);
		
		return result;
	}
	
	@Override
	public int countTotalUploadFiles(int teamNo) {
		return dao.countTotalUploadFiles(teamNo);
	}
	
	
}
