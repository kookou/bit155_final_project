package kr.or.bit3004.kanban;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit3004.comment.KanbanComment;
import kr.or.bit3004.dao.KanbanDao;
import kr.or.bit3004.kanban.KanbanUpload;

@Service
public class KanbanServiceImpl implements KanbanService {
	
	@Autowired
	private KanbanDao dao;
	
	@Override
	public int insertListTitle(KanbanList kanbanlist, Principal principal) {
		int newKanbanListNo = 0;
		kanbanlist.setId(principal.getName());
		
		//여기 트랜젝션 처리해야함
		dao.insertListTitle(kanbanlist);
		newKanbanListNo = dao.getANewKanbanListNo();
		System.out.println(newKanbanListNo);
		
		return newKanbanListNo;
	}
	
	
	@Override
	public KanbanList updateKanbanListTitle(KanbanList kanbanlist, Principal principal) {
		kanbanlist.setId(principal.getName());
		
		dao.updateKanbanListTitle(kanbanlist);
		KanbanList newKanbanList = dao.getAKanbanListByKanbanListNo(kanbanlist.getKanbanListNo());
		System.out.println("newKanbanList:"+newKanbanList);
		return newKanbanList;
	}
	
	
	@Override
	public List<Map> allKanbanList(int allBoardListNo){
		return dao.allKanbanList(allBoardListNo);
	}

	
	@Override
	public List<Map> kanbanCardList(){
		return dao.kanbanCardList();
	}
	
	@Override
	public List<Map> kanbanListJoinCard(int allBoardListNo){
		return dao.kanbanListJoinCard(allBoardListNo);
		
	}
	
	//delete kanban list
	@Override
	public void deleteKanbanList(int kanbanListNo) {
		System.out.println("deleteKanbanList");
		System.out.println("kanbanListNo : " + kanbanListNo);
		dao.deleteKanbanList(kanbanListNo);
		
	}
	

	@Override
	public List<Map> kanbanList(int teamNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int insertCardTitle(String title , int kanbanListNo) {
		int newcardNo;
		dao.insertCardTitle(title, kanbanListNo);
		newcardNo = dao.getANewCardNo();
		System.out.println(newcardNo);
		
		return newcardNo;
	}
	@Override
	public List<KanbanList> kanbanListFromAllBoardListNo(int allBoardListNo) {
		return dao.kanbanListFromAllBoardListNo(allBoardListNo);
	}
	
	@Override
	public void kanbanCardTitleUpdate(String title , int cardNo) {
		dao.updateCardTitle(title, cardNo);
	}
	
	@Override
	public KanbanCard kanbanCardContentSelect(int cardNo) {
		return dao.kanbanCardContent(cardNo);
	}
	
	@Override
	public void kanbanCardDescrioptionUpdate(String content, int cardNo) {
		dao.updateCardDescrioption(content,cardNo);
	}
	@Override
	public int insertCardReply(String content, int cardNo, String id) {
		return dao.insertCardReply(content, cardNo, id);
	}

	@Override
	public void deleteKanbanCard(int cardNo) {
		dao.deleteKanbanCard(cardNo);
	}
	@Override
	public List<KanbanComment> getKanbanCommentList(int cardNo){
		return dao.getKanbanCommentList(cardNo);
	}


	@Override
	public List<String> kanbanFilesUpload(MultipartHttpServletRequest request) {
		System.out.println("= kanbanFilesUpload Impl =");
		
		List<MultipartFile> fileList = request.getFiles("kanbanFiles");
		int allBoardListNo = Integer.parseInt(request.getParameter("allBoardListNo"));
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
				
		List<String> fileNames = new ArrayList<String>(); //		ajax return용 업로드파일목록
		
//		System.out.println("fileListSize : "+fileList.size());
		
		if(fileList != null && fileList.size() >0) {
			for(MultipartFile multiFile : fileList) {
				String originFileName = multiFile.getOriginalFilename();
				
				UUID uuid = UUID.randomUUID();				
				String fileName = uuid.toString() + originFileName;
//				System.out.println(fileName);
				
				String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\cloud"; 
				String filePath = path + "\\" + fileName;
				KanbanUpload kanbanUpload = new KanbanUpload();
				
				if((!fileName.equals("")) && (multiFile.getSize() > 0)) { // 파일 업로드					
					FileOutputStream fs = null;
					
					try {
						
						fs = new FileOutputStream(filePath);
						fs.write(multiFile.getBytes());
						
					} catch (Exception e) {
						System.out.println("file write error");
						e.getMessage();
					}finally {
						try {
							fs.close();
						} catch (IOException e) {
							System.out.println("fs close error");
							e.getMessage();
						}
					} // finally end					
				} else{ // if end
					System.out.println("제목이 없거나 빈 파일입니다.");
					continue;
				}
				
				
				// dto만들어서 이렇게 넣을거면 걍 HashMap으로 넣는거랑 뭐가 다르지...
				kanbanUpload.setOriginFileName(originFileName);
				kanbanUpload.setFileName(fileName); 
				kanbanUpload.setFileSize(multiFile.getSize());
				kanbanUpload.setAllBoardListNo(allBoardListNo);
				kanbanUpload.setCardNo(cardNo);
				
				
				
//				여기에 dao 불러서 file을 DB에 추가하는 내용 들어가야함
				dao.insertKanbanUploadFile(kanbanUpload);
				
				 //파일명 별도관리 (ajax return). 
				//근데 이름만 보낼게 아니라 파일 객체를 리스트로 보내야할 것 같은데..
				fileNames.add(fileName);
			
			} // for end
		} // if end		
		return fileNames;
	}

}
