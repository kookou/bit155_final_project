package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.dto.GroupAndTeam;

public interface TeamMainDao {
	public List<GroupAndTeam> getGroupAndTeam(String id);
}
