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
	
	public int getANewKanbanListNo();
	public int getANewCardNo();
	
	public List<Map> kanbanCardList();

	public List<Map> kanbanListJoinCard(int allBoardListNo);

	public List<Map> kanbanList(int teamNo);
	
	//delete kanban list
	public void deleteKanbanList(int kanbanListNo);
	
	//get A KanbanList By KanbanListNo
	public KanbanList getAKanbanListByKanbanListNo(int kanbanListNo);
	
	//update Kanban List Index
	public void updateKanbanListIndex(int kanbanListNo, int endListIDX);
	
	//resort Kanban List Index(S to B)
	public void resortKanbanListIndexSTB(int kanbanListNo, int startListIDX, int endListIDX);
	
	//resort Kanban List Index(B to S)
	public void resortKanbanListIndexBTS(int kanbanListNo, int startListIDX, int endListIDX);
	
	

	public void deleteKanbanList(String listTitle);
	public int insertCardTitle(String title ,  int cardIndex, int kanbanListNo);
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo);
	public void updateCardTitle(String title , int cardNo);
	public KanbanCard kanbanCardContent(int cardNo);

	public void updateCardDescrioption(String content,int cardNo);
	
	
	//update a Kanban Card Index
	public void updateKanbanCardIndex(int kanbanCardNo, int endCardIDX);
	
	//resort Kanban Cards Index(S to B)
	public void resortKanbanCardIndexSTB(int endListNo, int kanbanCardNo, int startCardIDX, int endCardIDX);
	
	//resort Kanban Cards Index(B to S)
	public void resortKanbanCardIndexBTS(int endListNo, int kanbanCardNo, int startCardIDX, int endCardIDX);
	
	//update a Kanban Card Index Between Lists
	public void updateKanbanCardIndexBL(int kanbanCardNo, int endListNo, int endCardIDX);

	//resort Start Point Kanban Cards Index
	public void resortStartKanbanCardIndex(int startListNo,int startCardIDX);
		
	//resort End Point Kanban Cards Index
	public void resortEndKanbanCardIndex(int endListNo, int endCardIDX, int kanbanCardNo);

	
	
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
