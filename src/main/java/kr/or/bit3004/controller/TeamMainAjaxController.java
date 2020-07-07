package kr.or.bit3004.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit3004.dto.GroupAndTeam;
import kr.or.bit3004.service.TeamMainService;

@RestController
public class TeamMainAjaxController {

	@Autowired
	private TeamMainService service;
	
	@RequestMapping("groupName.do")
	public List<GroupAndTeam> selectGroupName(String id) {
		return service.selectGroupName(id);
	}
	
	@RequestMapping("teamMain2.do")
	public List<GroupAndTeam> selectTeamName2(String id) {
		return service.selectGroupAndTeam(id);
	}
	
}
