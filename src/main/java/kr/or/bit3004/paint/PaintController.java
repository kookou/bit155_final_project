package kr.or.bit3004.paint;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.bit3004.aside.AsideService;

@Controller	
public class PaintController {

	@Autowired
	private AsideService asideService;
	
	@RequestMapping("paint.do")
	public String chat(Model model, RedirectAttributes rttr, HttpSession session,int teamNo) {
		if(session.getAttribute("id")==null) {
			model.addAttribute("team", asideService.getTeam(teamNo));
			model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
			model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
			rttr.addFlashAttribute("msg","You need to login first");
			System.out.println("로그인해야..");
			
			//return "redirect:/main"; 로그인구현하면 풀기!!!!!★★
			return "paint/paint";
		}
		
		model.addAttribute("team", asideService.getTeam(teamNo));
		model.addAttribute("teamMember", asideService.getTeamMember(teamNo));
		model.addAttribute("allBoardList", asideService.getAllBoardList(teamNo));
		
		return "paint/paint";
	}
	
}
