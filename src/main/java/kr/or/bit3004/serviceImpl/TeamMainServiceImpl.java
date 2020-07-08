package kr.or.bit3004.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.TeamMainDao;
import kr.or.bit3004.dto.GroupAndTeam;
import kr.or.bit3004.service.TeamMainService;

@Service
public class TeamMainServiceImpl implements TeamMainService {
	
	@Autowired
	private TeamMainDao dao;
	
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
	public void insertGroup(GroupAndTeam group) {
		dao.insertGroup(group);
	}
	
}
