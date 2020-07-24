package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.timeline.Timeline;

public interface TimelineDao {
	public List<Timeline> getTimeline(int teamNo);
	
	//chart
	// 팀별 전체 to do 수 구하기
	public int countTotalTodos(int teamNo);
		
	// 팀별 전체 칸반카드 수  구하기
	public int countTotalKanbanCards(int teamNo);
		
	// 팀별 전체 게시물 수 구하기
	public int countTotalBoards(int teamNo);
		
	// 팀별 전체 업로드 파일 수 구하기
	public int countTotalUploadFiles(int teamNo);
	
	
	
	// 팀별 오늘 작성된 칸반 카드 수 구하기
	public int countTodaysNewKanbanCards(int teamNo);
	// 팀별 오늘 작성된 게시물 수 구하기	
	public int countTodaysNewBoards(int teamNo);
	
	
}
