package kr.or.bit3004.aside;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit3004.dao.AsideDao;
import kr.or.bit3004.groupAndTeam.Team;
import kr.or.bit3004.groupAndTeam.TeamMember;

@Service
public class AsideServiceImpl implements AsideService {
	
	@Autowired
	private AsideDao dao;
	
	@Override
	public Team getTeam(int teamNo) {
		return dao.getTeam(teamNo);
	}
	
	@Override
	public List<TeamMember> getTeamMember(int teamNo) {
		return dao.getTeamMember(teamNo);
	}
	
	@Override
	public List<AllBoardList> getAllBoardList(int teamNo) {
		return dao.getAllBoardList(teamNo);
	}
	
}
