package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.timeline.ChartData;
import kr.or.bit3004.timeline.Timeline;

public interface TimelineDao {
	public List<Timeline> getTimeline(int teamNo);
	
	//chart
	// 팀별 전체 to do 수 구하기
	public int countTotalTodos(int teamNo);
		
	// 팀별 전체 칸반카드 수  구하기(view 사용)
	public int countTotalKanbanCards(int teamNo);
		
	// 팀별 전체 게시물 수 구하기(view 사용)
	public int countTotalBoards(int teamNo);
	
	// 팀별 전체 게시물(칸반카드 + 게시글) 수 구하기(view 사용) 
	public int countTotalPosts(int teamNo);
	
	// 팀별 전체 업로드 파일 수 구하기
	public int countTotalUploadFiles(int teamNo);
	
	// 팀별 오늘 작성된 게시물(칸반+게시판) 수 구하기
	public List<ChartData> countTodaysNewPosts(int teamNo);
	
	// 팀별 오늘 작성된 칸반 카드 수 구하기
	public int countTodaysNewKanbanCards(int teamNo);
	// 팀별 오늘 작성된 게시글 수 구하기	
	public int countTodaysNewBoards(int teamNo);
	
	// 팀원별 전체 작성한 게시물 수 구하기(view 사용)
	public List<ChartData> countTotalPostsByMember(int teamNo);
	
	
	//팀별 전체 TODO 수, 완료된 TODO 수 구하기
	public List<ChartData> getTodoCountByState(int teamNo);
	
	
	
}
