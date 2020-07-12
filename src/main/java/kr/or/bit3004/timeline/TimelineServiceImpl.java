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
	
	
	
}
