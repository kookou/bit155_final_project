package kr.or.bit3004.dao;

import java.util.List;
import java.util.Map;

import kr.or.bit3004.comment.KanbanComment;
import kr.or.bit3004.kanban.KanbanCard;
import kr.or.bit3004.kanban.KanbanList;
import kr.or.bit3004.kanban.KanbanUpload;

public interface KanbanDao {
	


	public int insertListTitle(KanbanList kanbanlist);

	public void updateKanbanListTitle(KanbanList kanbanlist);
	
	public List<Map> allKanbanList(int allBoardListNo);

	public int getANewKanbanListNo();
	public int getANewCardNo();
	
	public List<Map> kanbanCardList();

	public List<Map> kanbanListJoinCard(int allBoardListNo);

	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);
	
	//get A KanbanList By KanbanListNo
	public KanbanList getAKanbanListByKanbanListNo(int kanbanListNo);

	public void deleteKanbanList(String listTitle);
	public int insertCardTitle(String title , int kanbanListNo);
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void updateCardTitle(String title , int cardNo);
	public KanbanCard kanbanCardContent(int cardNo);

	public void updateCardDescrioption(String content,int cardNo);
	public int insertCardReply(String content, int cardNo, String id);

	
	//delete Kanban Card
	public void deleteKanbanCard(int cardNo);
	
	//insert Kanban Card Upload File into DB
	public int insertKanbanUploadFile(KanbanUpload kanbanUpload);
	
	
	public List<KanbanComment> getKanbanCommentList(int cardNo);
	public void updateCardReply(String content, int commentNo);
	public void deleteCardReply(int commentNo);
	
	//select Kanban Card Files
	public List<KanbanUpload> getKanbanCardFiles(int fileNo);
	
	//select a Kanban Card File
	public KanbanUpload getAKanbanCardFile(int fileNo);
	
	//delete a Kanban Card File
	public void deleteKanbanCardFile(int fileNo);

}
