package kr.or.bit3004.aside;

import java.util.List;

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
	public User selectInvitedMemberInfo(String id);
	public void updateAsideBoardName(AllBoardList allBoardList);
}
