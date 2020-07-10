package kr.or.bit3004.timeline;

import java.util.List;

import kr.or.bit3004.groupAndTeam.Team;
import kr.or.bit3004.groupAndTeam.TeamMember;

public interface TimelineService {
	public List<Timeline> getTimeline(int teamNo);
	public Team getTeam(int teamNo);
	public List<TeamMember> getTeamMember(int teamNo);
}
