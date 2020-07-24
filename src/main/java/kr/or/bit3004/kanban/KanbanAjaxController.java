package kr.or.bit3004.kanban;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit3004.comment.KanbanComment;

@RestController
public class KanbanAjaxController {
	
	@Autowired
	private KanbanService service;
	

	@RequestMapping("InsertKanbanList.ajax")
	public String kanbanListInsert(KanbanList kanbanlist, Principal principal) {
		System.out.println("Controller kanbanListInsert");
		
		int newKanbanListNo = service.insertListTitle(kanbanlist, principal);
		String newKanbanListNoString = Integer.toString(newKanbanListNo);
		return newKanbanListNoString;
	}
	
	@RequestMapping("deleteKanbanList.ajax")
	public void deleteKanbanList(int kanbanListNo) {
		System.out.println("Controller deleteKanbanList");
		
		service.deleteKanbanList(kanbanListNo);
	}
	
	
	@RequestMapping("updateKanbanList.ajax")
	public KanbanList updateKanbanList(KanbanList kanbanlist, Principal principal) {
		System.out.println("Controller updateKanbanList");
		System.out.println(kanbanlist);
		return service.updateKanbanListTitle(kanbanlist, principal);
	}
	
	
	@RequestMapping("resortKanbanList.ajax")
	public void resortKanbanList(int allBoardListNo, int kanbanListNo, int startListIDX, int endListIDX) {
		System.out.println("Controller resortKanbanList");
		service.resortKanbanList(allBoardListNo, kanbanListNo, startListIDX, endListIDX);
	}
	
		
	@RequestMapping("InsertKanbanCard.ajax")
	public String kanbanCardInsert(String title, int cardIndex, int kanbanListNo) {
		int newcardNo = service.insertCardTitle(title, cardIndex, kanbanListNo);
		String newcardNoString = Integer.toString(newcardNo);
		return newcardNoString;
	}
	
	@RequestMapping("UpdateKanbanCard.ajax")
	public String kanbanCardTitleUpdate(String title , int cardNo) {
		service.kanbanCardTitleUpdate(title, cardNo);
		System.out.println("업데이트완료");
		return "redirect:kanban.do?teamNo=?&allBoardListNo=?";
	}
	
	@RequestMapping("CardContentSelect.ajax")
	public KanbanCard kanbanCardContentSelect(int cardNo) {
		System.out.println("cardselect");
		System.out.println(service.kanbanCardContentSelect(cardNo));
		return service.kanbanCardContentSelect(cardNo);
	}
	
	@RequestMapping("CardDescrioptionInsert.ajax")
	public String kanbanCardDescrioptionUpdate(String content, int cardNo) {
		System.out.println("카드 내용 업데이트");
		service.kanbanCardDescrioptionUpdate(content, cardNo);
		return "redirect:kanban.do?teamNo=1&allBoardListNo=1";
	}

	@RequestMapping("CardReplyInsert.ajax")
	public String kanbanCardReplyInsert(String content , int cardNo, String id) {
		service.insertCardReply(content, cardNo, id);
		return "redirect:kanban.do?teamNo=1&allBoardListNo=1";
	}

	
	@RequestMapping("deleteKanbanCard.ajax")
	public void deleteKanbanCard(int cardNo) {
		System.out.println("카드 내용 업데이트");
		service.deleteKanbanCard(cardNo);
	}
	
	@RequestMapping("resortKanbanCard.ajax")
	public void resortKanbanCard(int allBoardListNo, int kanbanCardNo, int startListNo, int endListNo, int startCardIDX, int endCardIDX) {
		System.out.println("Controller resortKanbanCard");
		service.resortKanbanCard(allBoardListNo, kanbanCardNo, startListNo, endListNo, startCardIDX, endCardIDX);
	}
	
	
	@RequestMapping("CardReplySelect.ajax")
	public List<KanbanComment> getKanbanCommentList(int cardNo){
		return service.getKanbanCommentList(cardNo);		
	}
	@RequestMapping("CardReplyUpdate.ajax")
	public void kanbanCardReplyupdate(String content , int commentNo) {
		service.updateCardReply(content, commentNo);
	}

	@RequestMapping(value="kanbanFilesUpload.ajax", method=RequestMethod.POST)
	public List<KanbanUpload> kanbanFilesUpload(MultipartHttpServletRequest request){
		System.out.println("= kanbanFilesUpload.ajax =");
		return service.kanbanFilesUpload(request);
	}
	
	@RequestMapping("CardReplyDelete.ajax")
	public void kanbanCardReplyDelete(int commentNo) {
		service.deleteCardReply(commentNo);
	}
	
	
	//카드 파일 목록 가져오기
	@RequestMapping("cardFilesSelect.ajax")
	public List<KanbanUpload> getKanbanCardFiles(int cardNo) {
		System.out.println(" rest controller ");
		return service.getKanbanCardFiles(cardNo);
	}
	
	//카드 파일  삭제하기
	@RequestMapping("cardFilesDelete.ajax")
	public List<KanbanUpload> deleteKanbanCardFile(int fileNo, int cardNo, int teamNo) {
		System.out.println(" rest controller ");
		return service.deleteKanbanCardFile(fileNo, cardNo, teamNo);
	}
	
	@RequestMapping("StartDragCardUpdate.ajax")
	public void dragCard(int[]cardNo , int[] cardIndex, int kanbanListNo) {
		service.dragCard(cardNo, cardIndex, kanbanListNo);
	}
}
 