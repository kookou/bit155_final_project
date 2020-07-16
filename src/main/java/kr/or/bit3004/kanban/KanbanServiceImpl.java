package kr.or.bit3004.kanban;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
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
		System.out.println(kanbanlist);
		
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
	public List<KanbanCard> kanbanCardList(){
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
	public int insertCardTitle(String title , int cardIndex, int kanbanListNo) {
		int newcardNo;
		dao.insertCardTitle(title, cardIndex, kanbanListNo);
		
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
	public void updateCardReply(String content, int commentNo) {
		dao.updateCardReply(content, commentNo);
	}


	@Override
	public List<KanbanUpload> kanbanFilesUpload(MultipartHttpServletRequest request) {
		System.out.println("= kanbanFilesUpload Impl =");
		
		List<MultipartFile> fileList = request.getFiles("kanbanFiles");
		int allBoardListNo = Integer.parseInt(request.getParameter("allBoardListNo"));
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int teamNo = Integer.parseInt(request.getParameter("teamNo"));
		System.out.println(teamNo);
		
		List<KanbanUpload> returnFileList = new ArrayList<KanbanUpload>(); //		ajax return용 업로드파일목록
		
		
		if(fileList != null && fileList.size() >0) {
			for(MultipartFile multiFile : fileList) {
				String originFileName = multiFile.getOriginalFilename();
				
				UUID uuid = UUID.randomUUID();				
				String fileName = uuid.toString() + originFileName;
				
				String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\cloud\\" + teamNo; 
				File folder = new File(path);
				
				//폴더가 없을경우 폴더 생성하기
				if(!folder.exists()) {
					try {
						folder.mkdir();
						
					} catch (Exception e) {
						System.out.println("폴더 생성 실패");
						e.getMessage();
					}
					System.out.println("팀 폴더가 생성되었습니다.");
				}
				
				
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
				kanbanUpload.setFilePath(filePath);
				kanbanUpload.setAllBoardListNo(allBoardListNo);
				kanbanUpload.setCardNo(cardNo);
				
//				여기에 dao 불러서 file을 DB에 추가하는 내용 들어가야함
				dao.insertKanbanUploadFile(kanbanUpload);
				
				//파일 객체 리스트를 보내도록 수정
				returnFileList.add(kanbanUpload);
			
			} // for end
		} // if end		
		return returnFileList;
	}
	
	public void deleteCardReply(int commentNo) {
		dao.deleteCardReply(commentNo);
	}


	@Override
	public List<KanbanUpload> getKanbanCardFiles(int cardNo) {
		return dao.getKanbanCardFiles(cardNo);
	}


	@Override
	public List<KanbanUpload> deleteKanbanCardFile(int fileNo, int cardNo, int teamNo) {
		List<KanbanUpload> fileList = null;
		
		//파일 이름만 필요한데 파일 객체 정보가 통째로 필요할까..?그건 좀 고민해보자
		KanbanUpload selectedFile = dao.getAKanbanCardFile(fileNo);
		String filePath = System.getProperty("user.dir") 
						+ "\\src\\main\\resources\\static\\cloud\\" + teamNo
						+ "\\" + selectedFile.getFileName();
		
		System.out.println(filePath);
		
		File file = new File(filePath);
		
		if(file.exists()) {
			if(file.delete()) {
				System.out.println("파일 삭제 성공");
			}else {
				System.out.println("파일 삭제 실패");
			}
		}else {
			System.out.println("파일이 존재하지 않습니다.");
		}
		
		//트랜젝션 처리하기
		dao.deleteKanbanCardFile(fileNo);
		fileList = dao.getKanbanCardFiles(cardNo);
		return fileList;
	}
	//드래그앤 드랍 카드 업데이트 (스타트 리스트)
//	@Override
//	public void dragCardUpdateCardno(int[] cardNo , int kanbanListNo) {
//		for(int i = 0; i <= cardNo.length; i++) {
//			dao.dragCardUpdateCardno(cardNo[i], kanbanListNo);
//		}
//	}
//	@Override
//	public void dragCardUpdateIndex(int[] cardIndex , int kanbanListNo) {
//		for(int i = 0; i <= cardIndex.length; i++) {
//			dao.dragCardUpdateIndex(cardIndex[i], kanbanListNo);
//		}
//	}
//	

	@Override
	public void resortKanbanList(int allBoardListNo, int kanbanListNo, int startListIDX, int endListIDX) {
		System.out.println("ServiceImpl resortKanbanList");
		dao.updateKanbanListIndex(kanbanListNo, endListIDX);
		
		int difference = Math.abs(endListIDX-startListIDX);
		System.out.println(difference);
		
		if(endListIDX-startListIDX > 0) {
			System.out.println("큰 index로 이동");
			// 중간 index들 -1
			dao.resortKanbanListIndexSTB(kanbanListNo, startListIDX, endListIDX);

			
		}else if(endListIDX-startListIDX < 0) {
			System.out.println("작은 index로 이동");
			// 중간 index들 +1
			dao.resortKanbanListIndexBTS(kanbanListNo, startListIDX, endListIDX);

		}
		
		
//		dao.resortKanbanListIndex(kanbanListNo, endListIDX);
		
	}
	
	@Override
	public void drag(int[]cardNo , int[] cardIndex, int kanbanListNo) {
		Map<Object, Object> cardnomap = new HashMap<Object, Object>();
		Map<Object, Object> cardindexmap = new HashMap<Object, Object>();
		for(int i = 0 ; i < cardNo.length; i++) {
			cardnomap.put(i, cardNo[i]);
			System.out.println(cardnomap.get(i));
		}
		for(int i = 0; i < cardIndex.length; i ++ ) {
			cardindexmap.put(i, cardIndex[i]);
			
		}
		for(int i = 0; i < cardNo.length; i ++) {
			dao.dragCardUpdate((int)cardnomap.get(i), (int)cardindexmap.get(i), kanbanListNo);
			System.out.println(cardnomap.get(i));
			System.out.println(cardnomap.get(i));
		}
		
	}

}
