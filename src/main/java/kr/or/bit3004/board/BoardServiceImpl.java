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
		System.out.println("글쓰기임");
	    //dao.updateRefer(board);
		int maxRefer = dao.getMaxRefer();
		int refer = maxRefer + 1;
		board.setRefer(refer);
		dao.insertBoard(board);
	}
	
	@Override
	public int getBoardNo() {
		return dao.getBoardNo();
	}
	
	//파일업로드
	@Override
	public List<String> insertBoardUploadFile(MultipartHttpServletRequest request) {
		System.out.println("= kanbanFilesUpload Impl =");
		
		List<MultipartFile> fileList = request.getFiles("file");
		int allBoardListNo = Integer.parseInt(request.getParameter("allBoardListNo"));
		int boardNo = getBoardNo();
		int teamNo = Integer.parseInt(request.getParameter("teamNo"));
		System.out.println(teamNo);
		
		List<String> fileNames = new ArrayList<String>(); //ajax return용 업로드파일목록
		
		System.out.println("fileListSize : "+fileList.size());
		
		if(fileList != null && fileList.size() >0) {
			for(MultipartFile multiFile : fileList) {
				String originFileName = multiFile.getOriginalFilename();
				
				UUID uuid = UUID.randomUUID();				
				String fileName = uuid.toString() + originFileName;
				System.out.println(fileName);
				
				String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\cloud\\" + teamNo; 
				File folder = new File(path);
				System.out.println(path);
				//폴더가 없을경우 폴더 생성하기
				//왜 나는 폴더가 안 만들어지지?;;;;
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
				BoardUpload boardUpload = new BoardUpload();
				
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
					}				
				} else{
					System.out.println("제목이 없거나 빈 파일입니다.");
					continue;
				}
				
				boardUpload.setOriginFileName(originFileName);
				boardUpload.setFileName(fileName); 
				boardUpload.setFilePath(filePath);
				boardUpload.setFileSize(multiFile.getSize());
				boardUpload.setAllBoardListNo(allBoardListNo);
				boardUpload.setBoardNo(boardNo);
				
				//dao 호출하여 DB에 저장하기
				dao.insertBoardUploadFile(boardUpload);
				fileNames.add(originFileName);
			
			}
		}
		return fileNames;
	}
	
	//파일 다운로드
	@Override
	public List<BoardUpload> selectBoardDownloadFile(int boardNo){
		return dao.selectBoardDownloadFile(boardNo);
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
		dao.updateReboardAddstep(board);
	//이거 주석풀까말까	board.setBoardNo(board.getBoardNo());
			//	System.out.println("리퍼"+dao.getMaxRefer());
		//1.현재 내가 읽은글의 refer, depth, step
		Board currBoardInfo = dao.getReferDepthStep(board.getBoardNo());
		System.out.println("boardno="+board.getBoardNo());
			//	System.out.println("getstep"+dao.getStep(currBoardInfo));
		//2.위치
		int step=dao.getStep(currBoardInfo);
		System.out.println("step:" + step);
		
		//답글 insert
		if(step==0) {
			System.out.println(board);
			System.out.println("currboard="+currBoardInfo);
			System.out.println("step==0을탔따.");
			int maxStep= dao.getMaxStep(currBoardInfo.getRefer()); //요녀석이 문제.
			System.out.println("된다");
			step = maxStep;
			System.out.println("된다");
		} else {
			System.out.println("step=0이 아닌걸탔따.");
			dao.updateStep(currBoardInfo);
		}
		
		board.setRefer(currBoardInfo.getRefer());
		board.setDepth(currBoardInfo.getDepth()+1);
		board.setStep(step);
		dao.insertReboard(board);
	}
	
}







