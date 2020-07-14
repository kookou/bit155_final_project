package kr.or.bit3004.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		dao.insertBoard(board);
	}
	
	//파일업로드
	/*
	@Override
	public void fileUploadBoard(Board board , HttpServletRequest request) {
		
		List<CommonsMultipartFile> files = board.getFiles();
		List<String> fileNames = new ArrayList<String>();
		
		if(files != null && files.size() > 0) {
			for(CommonsMultipartFile multifile : files) {
				String fileName = multifile.getOriginalFilename();
				String path = request.getServletContext().getRealPath("/upload");
				String fpath = path + "\\" + fileName;
				
				if(!fileName.equals("")) {
					try {
						FileOutputStream fs = new FileOutputStream(fpath);
						try {
							fs.write(multifile.getBytes());
							fs.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					fileNames.add(fileName);
				}
			}
		}
		//DB에 파일명 저장
		board.setFileName(fileNames.get(0));
		board.setFileName(fileNames.get(1));
		
		dao.insertBoard(board);
	}
	*/
	
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

}
