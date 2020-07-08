package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.dto.GroupAndTeam;

public interface TeamMainDao {
	public List<GroupAndTeam> getGroup(String id);
	public List<GroupAndTeam> getGroupAndTeam(String id);
	public int getCurrGroupNo();
	public void insertGroup(GroupAndTeam group);
	public void moveGroup(int groupNo);
	public void delGroup(int groupNo);
}
