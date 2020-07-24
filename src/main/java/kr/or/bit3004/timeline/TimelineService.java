package kr.or.bit3004.timeline;

import java.util.List;

public interface TimelineService {
	public List<Timeline> getTimeline(int teamNo);
	
//	chartInfo.put("totalTodos", value);
//	chartInfo.put("totalKanbanCards", value);
//	chartInfo.put("totalBoards", value);
//	chartInfo.put("totalFiles", value);
//	chartInfo.put("activeRate", value);
//	chartInfo.put("progress", value);
//	chartInfo.put("todaysNew", value);
	
	//chart
	// 팀별 전체 to do 수 구하기
	public int countTotalTodos(int teamNo);		
	// 팀별 전체 게시물(칸반+게시판) 수  구하기
	public int countTotalPosts(int teamNo);		
	// 팀별 전체 업로드 파일 수 구하기
	public int countTotalUploadFiles(int teamNo);	
//	// 팀별 오늘 작성된 칸반 카드 수 구하기
//	public int countTodaysNewKanbanCards(int teamNo);
//	// 팀별 오늘 작성된 게시물 수 구하기	
//	public int countTodaysNewBoards(int teamNo);
	
	
	
	
	
}
