package kr.or.bit3004.groupAndTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamMainAjaxController {

	@Autowired
	private TeamMainService service;
	
	@RequestMapping("teamList.do")
	public Map<String, Object> selectTeamName(String id, Model model) {
		Map<String, Object> groupAndTeam = new HashMap<>();
		groupAndTeam.put("groupAndTeam", service.selectGroupAndTeam(id));
		groupAndTeam.put("group", service.selectGroupName(id));
		return groupAndTeam;
	}
	
	@RequestMapping("groupName.do")
	public List<GroupAndTeam> selectGroupName(String id) {
		return service.selectGroupName(id);
	}
	
	@RequestMapping("teamMain2.do")
	public List<GroupAndTeam> selectTeamName2(String id) {
		return service.selectGroupAndTeam(id);
	}
	
	@RequestMapping("updateGroupName.do")
	public void updateGroupName(GroupAndTeam group) {
		service.updateGroupName(group);
	}
	
	@RequestMapping("insertGroup.do")
	public int insertGroup(GroupAndTeam group) {
		System.out.println(group);
		service.insertGroup(group);
		return service.getCurrGroupNo();
	}
	
	@RequestMapping("delGroup.do")
	public void delGroup(GroupAndTeam group) {
		service.moveAndDelGroup(group);
	}
	
	@RequestMapping("searchUser.do")
	public List<String> searchUser(String id) {
		return service.searchUser(id);
	}
	
	@RequestMapping("insertTeam.do")
	public int insertTeam(GroupAndTeam team) {
		service.insertTeam(team);
		return service.getCurrTeamNo();
	}
	
	@RequestMapping("moveTeamFromGroup.do")
	public Map<String, Object> moveTeamFromGroup(GroupAndTeam group) {
		return service.moveTeamFromGroup(group);
	}
	
}
