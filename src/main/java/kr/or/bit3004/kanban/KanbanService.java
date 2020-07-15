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

	public List<Map> kanbanCardList();
	public List<Map> kanbanListJoinCard(int allBoardListNo);


	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);
	
	//update Kanban List
	public KanbanList updateKanbanListTitle(KanbanList kanbanlist, Principal principal);
	
	public int insertCardTitle(String title , int cardIndex, int kanbanListNo);
	
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void kanbanCardTitleUpdate(String title , int cardNo);
	public KanbanCard kanbanCardContentSelect(int cardNo);
	public void kanbanCardDescrioptionUpdate(String content,int cardNo);
	public int insertCardReply(String content, int cardNo, String id);
	
	//delete Kanban Card
	public void deleteKanbanCard(int cardNo);
	
	//upload file to Kanban Card
	public List<String> kanbanFilesUpload(MultipartHttpServletRequest request);
	
	public List<KanbanComment> getKanbanCommentList(int cardNo);
	public void updateCardReply(String content, int commentNo);
	public void deleteCardReply(int commentNo);
	
	//select Kanban Card Files
	public List<KanbanUpload> getKanbanCardFiles(int cardNo);
	
	//delete a Kanban Card File
	public List<KanbanUpload> deleteKanbanCardFile(int fileNo, int cardNo, int teamNo);

}
