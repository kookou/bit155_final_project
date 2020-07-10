package kr.or.bit3004.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TeamMainDao;
import kr.or.bit3004.dao.TimelineDao;
import kr.or.bit3004.groupAndTeam.Team;
import kr.or.bit3004.groupAndTeam.TeamMember;

@Service
public class TimelineServiceImpl implements TimelineService {
	
	@Autowired
	private TimelineDao timelineDao;
	@Autowired
	private TeamMainDao teamMainDao;
	
	@Override
	public List<Timeline> getTimeline(int teamNo) {
		return timelineDao.getTimeline(teamNo);
	}
	
	@Override
	public Team getTeam(int teamNo) {
		return teamMainDao.getTeam(teamNo);
	}
	
	@Override
	public List<TeamMember> getTeamMember(int teamNo) {
		return teamMainDao.getTeamMember(teamNo);
	}
	
}
