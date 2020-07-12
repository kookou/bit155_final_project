package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.groupAndTeam.GroupAndTeam;

public interface TeamMainDao {
	public List<GroupAndTeam> getGroup(String id);
	public List<GroupAndTeam> getGroupAndTeam(String id);
	public int getCurrGroupNo();
	public void updateGroupName(GroupAndTeam group);
	public void insertGroup(GroupAndTeam group);
	public int searchPersonalNo(String id);
	public void moveGroup(Map<String, Integer> groupNo);
	public void delGroup(int groupNo);
	public void insertTeam(GroupAndTeam team);
	public void insertTeamLeader(GroupAndTeam team);
	public int getCurrTeamNo();
	public void moveTeamFromGroup(GroupAndTeam groupAndTeam);
}
