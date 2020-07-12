package kr.or.bit3004.groupAndTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TeamMainDao;

@Service
public class TeamMainServiceImpl implements TeamMainService {
	
	@Autowired
	private TeamMainDao dao;
	
	@Autowired
	private TeamMainDao teamMainDao;

	
	@Override
	public List<GroupAndTeam> selectGroupName(String id) {
		return dao.getGroup(id);
	}
	
	@Override
	public List<GroupAndTeam> selectGroupAndTeam(String id) {
		return dao.getGroupAndTeam(id);
	}
	
	@Override
	public int getCurrGroupNo() {
		return dao.getCurrGroupNo();
	}
	
	@Override
	public void updateGroupName(GroupAndTeam group) {
		dao.updateGroupName(group);
	}
	
	@Override
	public void insertGroup(GroupAndTeam group) {
		dao.insertGroup(group);
	}
	
	@Override
	public void moveAndDelGroup(GroupAndTeam group) {
		Map<String, Integer> map = new HashMap<>();
		map.put("1st", dao.searchPersonalNo(group.getId()));
		map.put("2nd", group.getGroupNo());
		dao.moveGroup(map);
		dao.delGroup(group.getGroupNo());
	}
	
	@Override
	public List<String> searchUser(String id) {
		return dao.searchUser(id);
	}
	
	@Override
	public void insertTeam(GroupAndTeam team) {
		dao.insertTeam(team);
	}
	
	@Override
	public void insertTeamLeader(GroupAndTeam team) {
		dao.insertTeamLeader(team);
	}
	
	@Override
	public int getCurrTeamNo() {
		return dao.getCurrTeamNo();
	}
	
	@Override
	public Map<String, Object> moveTeamFromGroup(GroupAndTeam group) {
		dao.moveTeamFromGroup(group);
		Map<String, Object> groupAndTeam = new HashMap<>();
		groupAndTeam.put("groupAndTeam", dao.getGroupAndTeam(group.getId()));
		groupAndTeam.put("group", dao.getGroup(group.getId()));
		return groupAndTeam;
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
