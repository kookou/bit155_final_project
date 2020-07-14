package kr.or.bit3004.chatting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChattingController {
	
	@RequestMapping("chatting.do")
	public void chatting() {}
	
}
