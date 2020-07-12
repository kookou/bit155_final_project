package kr.or.bit3004.aside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsideAjaxController {
	
	@Autowired
	private AsideService service;
	
	@RequestMapping("insertAllBoard.do")
	public int insertAllBoard(AllBoardList allBoard) {
		service.insertAllBoard(allBoard);
		return service.getCurrAllBoardListNo();
	}
	
}
