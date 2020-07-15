package kr.or.bit3004.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.bit3004.dao.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDao dao;

	//게시판 목록보기 
	@Override
	public List<Board> selectBoardList(int allBoardListNo){
		return dao.getBoardList(allBoardListNo);
	}

	//게시판 상세보기
	@Override
	public Board selectBoardByBoardNo(int boardNo) {
		return dao.selectBoardByNo(boardNo);
	}
	
	//게시판 조회수 증가
	public void updateReadCount(int boardNo) {
		dao.updateReadCount(boardNo);
	}
	
	//게시판 글쓰기
	@Override
	public void insertBoard(Board board) {
//		dao.updateRefer(board);
		int maxRefer = dao.getMaxRefer();
		int refer = maxRefer + 1;
		board.setRefer(refer);
		dao.insertBoard(board);
	}
	
	//파일업로드
	public List<String> boardFilesUpload(MultipartHttpServletRequest request){
		System.out.println("= boardFilesUpload Impl =");
		
		List<MultipartFile> fileList = request.getFiles("boardFiles");
		int allBoardListNo = Integer.parseInt(request.getParameter("allBoardListNo"));
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		int teamNo = Integer.parseInt(request.getParameter("teamNo"));
		System.out.println(teamNo);
		
		List<String> fileNames = new ArrayList<String>();
		
		if(fileList != null && fileList.size() > 0) {
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
						System.out.println("폴더생성실패");
						e.getMessage();
					}
					System.out.println("팀 폴더가 생성되었습니다.");
					
					String filePath = path + "\\"  + fileName;
					BoardUpload boardUpload = new BoardUpload();
					
					if((!fileName.equals("")) && (multiFile.getSize() > 0)) { //파일업로드
						FileOutputStream fs = null;
						
						try {
							fs = new FileOutputStream(filePath);
							fs.write(multiFile.getBytes());
						} catch (Exception e) {
							System.out.println("file write error");
							e.getMessage();
						} finally {
							try {
								fs.close();
							} catch (IOException e) {
								System.out.println("fs close error");
								e.getMessage();
							}
						} //finally end
					} else { //if end
						System.out.println("제목이 없거나 빈 파일입니다.");
						continue;
					}
					
					boardUpload.setFileOriginName(originFileName);
					boardUpload.setFileName(fileName);
					boardUpload.setFileSize(multiFile.getSize());
					boardUpload.setAllBoardListNo(allBoardListNo);
					boardUpload.setBoardNo(boardNo);
					
					//dao 불러서 file을 DB에 추가하는 내용 들어가야함
					dao.insertBoardUploadFile(boardUpload);
					fileNames.add(originFileName);
				}//for end
			}//if end
		}
	return fileNames;
	}
	
	//게시판 수정하기
	@Override
	public void updateBoard(Board board) {
		dao.updateBoard(board);
	}
	
	//게시판 삭제하기
	@Override
	public void deleteBoard(int boardNo) {
		dao.deleteBoard(boardNo);
	}

	//게시판 답글쓰기
	@Override
	public void insertReboard(Board board) {
		System.out.println(board);
		dao.updateReboardAddstep(board);
		
			//	System.out.println("리퍼"+dao.getMaxRefer());
		//1.답글
		Board currBoardInfo = dao.getReferDepthStep(board.getBoardNo());
			//	System.out.println("getstep"+dao.getStep(currBoardInfo));
		//2.위치
		int step=dao.getStep(currBoardInfo);
		//답글 insert
		
		if(step==0) {
			System.out.println("step==0을탔따.");
			int maxStep= dao.getMaxStep(currBoardInfo);
			board.setStep(maxStep);
		} else {
			System.out.println("step=0이 아닌걸탔따.");
			dao.updateStep(currBoardInfo);
		}
		
		board.setDepth(currBoardInfo.getDepth()+1);
		board.setRefer(currBoardInfo.getRefer());
		dao.insertReboard(board);
	}
	
	
}







