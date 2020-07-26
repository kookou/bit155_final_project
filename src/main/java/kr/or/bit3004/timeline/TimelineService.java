package kr.or.bit3004.timeline;

import java.util.List;


public interface TimelineService {
	public List<Timeline> getTimeline(int teamNo);
	
	//chart
	// 팀별 전체 to do 수 구하기
	public int countTotalTodos(int teamNo);		
	// 팀별 전체 게시물(칸반+게시판) 수  구하기
	public int countTotalPosts(int teamNo);		
	// 팀별 전체 업로드 파일 수 구하기
	public int countTotalUploadFiles(int teamNo);
	//회원별 활동 비율 구하기
	public String getActiveRate(int teamNo);
	//프로젝트 진행률 구하기
	public int getProgress(int teamNo);
	//당일 신규 게시물수 구하기
	public String getTodaysNewPosts(int teamNo);
	
	
//	//차트 데이터 만들기 (ServiceImple안에서 사용 - 일단 필요 없음)
//	public void makeData(String label, String value, JsonArray chartData);
	
}
