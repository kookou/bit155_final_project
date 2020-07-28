package kr.or.bit3004.todoList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit3004.aside.AsideService;
import kr.or.bit3004.user.SessionUser;

@Controller
public class TodoListController {
	
	@Autowired
	private AsideService asideService;
	
	@RequestMapping("todoList.do")
	public String todoList(HttpSession session, Model model) {
//		model.addAttribute("todoList", service.selectTodoList(todoList));
		SessionUser currentUser = (SessionUser)session.getAttribute("currentUser");
	      //팀메인 페이지에서 session값(teamNo, teamMember) 리셋
	    int teamNo = currentUser.getTeamNo();
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		return "todoList/todoList";
	}
	
}
