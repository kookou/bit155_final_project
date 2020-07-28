package kr.or.bit3004.groupAndTeam;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.user.SessionUser;

@Controller
public class TeamMainController {

	@Autowired
	private TeamMainService service;
	
	/*
	* @Method Name : selectTeamName
	* @작성일 : 2020.07.28
	* @작성자 : 김혜린
	* @Method 설명 : 개인 group과 team List 가져오기 (ajax)
	* @param session
	* @param model
	* @return groupAndTeam List
	**/
	@RequestMapping("teamMain.do")
	public String selectTeamName(HttpSession session, Model model) {
		//session에서 id값 가져오기
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
		String id = currentUser.getId();
		//팀메인 페이지에서 session값(teamNo, teamMember) 리셋
		currentUser.setTeamNo(0);
		currentUser.setIsTeamLeader(null);
		
		model.addAttribute("groupAndTeam", service.selectGroupAndTeam(id));
		model.addAttribute("group", service.selectGroupName(id));
		model.addAttribute("teamMemberList", service.getTeamMemberList(id));
		return "teamMain/groupAndTeamMain";
	}
	
}
