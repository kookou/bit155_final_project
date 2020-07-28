package kr.or.bit3004.kanban;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit3004.comment.KanbanComment;
import kr.or.bit3004.user.SessionUser;

@RestController
public class KanbanAjaxController {
	
	@Autowired
	private KanbanService service;
	
	
	/**
	 * @Method Name : kanbanList
	 * @작성일 : 2020.07.28
	 * @작성자 : 박혜정
	 * @Method 설명 : kanban 게시판 초기 로딩시 필요한 동기 데이터. 비동기 처리를 위해 시퀀스로 부여된 리스트 넘버를 리턴해줌
	 * @param : session
	 * @param : allBoardListNo
	 * @param : model
	 * @return : newKanbanListNoString
	 **/
	@RequestMapping("InsertKanbanList.ajax")
	public String kanbanListInsert(KanbanList kanbanlist, HttpSession session) {
		int newKanbanListNo = service.insertListTitle(kanbanlist, session);
		String newKanbanListNoString = Integer.toString(newKanbanListNo);
		return newKanbanListNoString;
	}
	
	//리스트 타이틀 업데이트
	@RequestMapping("updateKanbanList.ajax")
	public KanbanList updateKanbanList(KanbanList kanbanlist, HttpSession session) {
		return service.updateKanbanListTitle(kanbanlist, session);
	}
	
	//리스트 삭제
	@RequestMapping("deleteKanbanList.ajax")
	public void deleteKanbanList(int kanbanListNo) {
		service.deleteKanbanList(kanbanListNo);
	}
	
	@RequestMapping("resortKanbanList.ajax")
	public void resortKanbanList(int allBoardListNo, int kanbanListNo, int startListIDX, int endListIDX) {
		service.resortKanbanList(allBoardListNo, kanbanListNo, startListIDX, endListIDX);
	}
	
	//칸반 카드 인서트
	@RequestMapping("InsertKanbanCard.ajax")
	public String kanbanCardInsert(String title, int cardIndex, int kanbanListNo, HttpSession session) {
		
		//소셜로그인 유저의 경우 princiapl의 name과 db의 id가 다르다.
		//principal 말고 session에서 받아와서 사용해야함 ㅠㅠㅠㅠㅠㅠ
		
		int newcardNo = service.insertCardTitle(title, cardIndex, kanbanListNo, session);
		String newcardNoString = Integer.toString(newcardNo);
		return newcardNoString;
	}
	
	//업데이트 칸반 카드
	@RequestMapping("UpdateKanbanCard.ajax")
	public String kanbanCardTitleUpdate(String title , int cardNo) {
		service.kanbanCardTitleUpdate(title, cardNo);
		return "redirect:kanban.do?teamNo=?&allBoardListNo=?";
	}
	
	//카드내용 셀렉트
	@RequestMapping("CardContentSelect.ajax")
	public KanbanCard kanbanCardContentSelect(int cardNo) {
		return service.kanbanCardContentSelect(cardNo);
	}
	
	//카드 내용 인서트
	@RequestMapping("CardDescrioptionInsert.ajax")
	public String kanbanCardDescrioptionUpdate(String content, int cardNo) {
		service.kanbanCardDescrioptionUpdate(content, cardNo);
		return "redirect:kanban.do?teamNo=1&allBoardListNo=1";
	}

	//카드 리플 인서트
	@RequestMapping("CardReplyInsert.ajax")
	public String kanbanCardReplyInsert(String content , int cardNo, String id) {
		service.insertCardReply(content, cardNo, id);
		return "redirect:kanban.do?teamNo=1&allBoardListNo=1";
	}

	//카드 삭제
	@RequestMapping("deleteKanbanCard.ajax")
	public void deleteKanbanCard(int cardNo) {
		service.deleteKanbanCard(cardNo);
	}
	
	
	@RequestMapping("resortKanbanCard.ajax")
	public void resortKanbanCard(int allBoardListNo, int kanbanCardNo, int startListNo, int endListNo, int startCardIDX, int endCardIDX) {
		service.resortKanbanCard(allBoardListNo, kanbanCardNo, startListNo, endListNo, startCardIDX, endCardIDX);
	}
	
	//카드 리플 셀렉트
	@RequestMapping("CardReplySelect.ajax")
	public List<KanbanComment> getKanbanCommentList(int cardNo){
		return service.getKanbanCommentList(cardNo);		
	}
	
	//카드 리플 업데이트
	@RequestMapping("CardReplyUpdate.ajax")
	public void kanbanCardReplyupdate(String content , int commentNo) {
		service.updateCardReply(content, commentNo);
	}

	//카드 파일 업로드
	@RequestMapping(value="kanbanFilesUpload.ajax", method=RequestMethod.POST)
	public List<KanbanUpload> kanbanFilesUpload(MultipartHttpServletRequest request){
		return service.kanbanFilesUpload(request);
	}
	
	//카드 리플 삭제
	@RequestMapping("CardReplyDelete.ajax")
	public void kanbanCardReplyDelete(int commentNo) {
		service.deleteCardReply(commentNo);
	}
	
	//카드 파일 목록 가져오기
	@RequestMapping("cardFilesSelect.ajax")
	public List<KanbanUpload> getKanbanCardFiles(int cardNo) {
		return service.getKanbanCardFiles(cardNo);
	}
	
	//카드 파일  삭제하기
	@RequestMapping("cardFilesDelete.ajax")
	public List<KanbanUpload> deleteKanbanCardFile(int fileNo, int cardNo, int teamNo) {
		return service.deleteKanbanCardFile(fileNo, cardNo, teamNo);
	}
	
	//카드 드래그앤 드롭 시 위치 변경
	@RequestMapping("StartDragCardUpdate.ajax")
	public void dragCard(int[]cardNo , int[] cardIndex, int kanbanListNo) {
		service.dragCard(cardNo, cardIndex, kanbanListNo);
	}
}
 