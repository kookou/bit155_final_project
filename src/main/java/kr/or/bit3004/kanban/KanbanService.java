package kr.or.bit3004.kanban;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit3004.comment.KanbanComment;

@Service
public interface KanbanService {
	//List title insert
	public int insertListTitle(KanbanList kanbanlist, Principal principal);

	public List<KanbanCard> kanbanCardList();
	public List<Map> kanbanListJoinCard(int allBoardListNo);


	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);
	
	//update Kanban List
	public KanbanList updateKanbanListTitle(KanbanList kanbanlist, Principal principal);
	
	//resort Kanban List
	public void resortKanbanList(int allBoardListNo, int kanbanListNo, int startListIDX, int endListIDX);
	
	public int insertCardTitle(String title , int cardIndex, int kanbanListNo);
	
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void kanbanCardTitleUpdate(String title , int cardNo);
	public KanbanCard kanbanCardContentSelect(int cardNo);
	public void kanbanCardDescrioptionUpdate(String content,int cardNo);
	public int insertCardReply(String content, int cardNo, String id);
	
	//delete Kanban Card
	public void deleteKanbanCard(int cardNo);
	
	//resort Kanban Card
	public void resortKanbanCard(int allBoardListNo, int kanbanCardNo, int startListNo, int endListNo, int startCardIDX, int endCardIDX);

	
	//upload file to Kanban Card
	public List<KanbanUpload> kanbanFilesUpload(MultipartHttpServletRequest request);
	
	public List<KanbanComment> getKanbanCommentList(int cardNo);
	public void updateCardReply(String content, int commentNo);
	public void deleteCardReply(int commentNo);
	
	//select Kanban Card Files
	public List<KanbanUpload> getKanbanCardFiles(int cardNo);
	
	//delete a Kanban Card File
	public List<KanbanUpload> deleteKanbanCardFile(int fileNo, int cardNo, int teamNo);
//	public void dragCardUpdateCardno(int[] cardNo , int[] cardIndex,int kanbanListNo);
//	public void dragCardUpdateCardno(int[] cardNo , int kanbanListNo);
//	public void dragCardUpdateIndex(int[] cardIndex , int kanbanListNo);
	public void dragCard(int[]cardNo , int[] cardindex, int kanbanListNo);
}
