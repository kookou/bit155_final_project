package kr.or.bit3004.groupAndTeam;

import java.util.List;
import java.util.Map;

public interface TeamMainService {
	public List<GroupAndTeam> selectGroupName(String id);
	public List<GroupAndTeam> selectGroupAndTeam(String id);
	public int getCurrGroupNo();
	public void updateGroupName(GroupAndTeam group);
	public void insertGroup(GroupAndTeam group);
	public void moveAndDelGroup(GroupAndTeam group);
	public void insertTeam(GroupAndTeam team);
	public void insertTeamLeader(GroupAndTeam team);
	public int getCurrTeamNo();
	public Map<String, Object> moveTeamFromGroup(GroupAndTeam group);
}
