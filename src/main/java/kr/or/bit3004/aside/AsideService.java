package kr.or.bit3004.aside;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.groupAndTeam.GroupAndTeam;
import kr.or.bit3004.groupAndTeam.Team;
import kr.or.bit3004.groupAndTeam.TeamMember;
import kr.or.bit3004.user.User;

public interface AsideService {
	public Team getTeam(int teamNo);
	public List<TeamMember> getTeamMember(int teamNo);
	public List<AllBoardList> getAllBoardList(int teamNo);
	public void insertAllBoard(AllBoardList allBoard);
	public int getCurrAllBoardListNo();
	public List<String> searchUser(String id);
	public void inviteMember(GroupAndTeam groupAndTeam);
	public void deleteTeamMember(GroupAndTeam groupAndTeam);
	public void deleteGroupTeam(GroupAndTeam groupAndTeam);
	public void updateNewLeader(Map<String, Object> newLeaderAndTeamNo);
	public User selectInvitedMemberInfo(String id);
	public void updateAsideBoardName(AllBoardList allBoardList);
	public void deleteAsideBoard(AllBoardList allBoardList);
	public void updateTeamName(Team team);
	
	// id와 teamNo로 팀리더 여부 가져오기
	public String isTeamLeader(String id, int teamNo);
}
