package kr.or.bit3004.aside;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/*
	* @Method Name : insertAllBoard
	* @작성일 : 2020.07.28
	* @작성자 : 김혜린
	* @Method 설명 : Board 추가하기
	* @param allBoard
	* @return 들어간 Board의 No값
	**/
	@RequestMapping("insertAllBoard.do")
	public int insertAllBoard(AllBoardList allBoard) {
		service.insertAllBoard(allBoard);
		return service.getCurrAllBoardListNo();
	}

	@RequestMapping("searchUser.do")
	public List<String> searchUser(String id) {
		return service.searchUser(id);
	}
	
	/*
	* @Method Name : selectInvitedMemberInfo
	* @작성일 : 2020.07.28
	* @작성자 : 김혜린
	* @Method 설명 : 팀원 초대하기
	* @param groupAndTeam
	* @return 검색한 회원의 정보
	**/
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
	
	@RequestMapping("leaderTeamOut.do")
	public void updateNewLeader(GroupAndTeam groupAndTeam, String newLeader) {
		Map<String, Object> newLeaderAndTeamNo = new HashMap<>();
		newLeaderAndTeamNo.put("newLeader", newLeader);
		newLeaderAndTeamNo.put("teamNo", groupAndTeam.getTeamNo());
		service.updateNewLeader(newLeaderAndTeamNo);
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
