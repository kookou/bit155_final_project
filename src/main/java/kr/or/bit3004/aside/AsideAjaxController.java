package kr.or.bit3004.aside;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit3004.groupAndTeam.GroupAndTeam;
import kr.or.bit3004.groupAndTeam.Team;
import kr.or.bit3004.user.User;

@RestController
public class AsideAjaxController {
	
	@Autowired
	private AsideService service;
	
	@RequestMapping("insertAllBoard.do")
	public int insertAllBoard(AllBoardList allBoard) {
		service.insertAllBoard(allBoard);
		return service.getCurrAllBoardListNo();
	}

	@RequestMapping("searchUser.do")
	public List<String> searchUser(String id) {
		return service.searchUser(id);
	}
	
	@RequestMapping("inviteMember.do")
	public User selectInvitedMemberInfo(GroupAndTeam groupAndTeam) {
		service.inviteMember(groupAndTeam);
		return service.selectInvitedMemberInfo(groupAndTeam.getId());
	}
	
	@RequestMapping("teamOut.do")
	public void deleteTeamMember(GroupAndTeam groupAndTeam) {
		service.deleteTeamMember(groupAndTeam);
		service.deleteGroupTeam(groupAndTeam);
	}
	
	@RequestMapping("editBoardName.do")
	public void updateAsideBoardName(AllBoardList allBoardList) {
		service.updateAsideBoardName(allBoardList);
	}

	@RequestMapping("delBoard.do")
	public void deleteAsideBoard(AllBoardList allBoardList) {
		service.deleteAsideBoard(allBoardList);
	}
	
	@RequestMapping("editTeamName.do")
	public void updateTeamName(Team team) {
		service.updateTeamName(team);
	}
	
}
