package kr.or.bit3004.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller	
public class PaintController {

	@RequestMapping("/paint.do")
	public String chat(Model model, RedirectAttributes rttr, HttpSession session) {
		if(session.getAttribute("id")==null) {
			rttr.addFlashAttribute("msg","You need to login first");
			System.out.println("로그인해야..");
			
			//return "redirect:/main"; 로그인구현하면 풀기!!!!!★★
			return "/paint/paint";
		}
		
	
		
		return "paint/paint";
	}
	
}
