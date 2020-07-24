package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.timeline.Timeline;

public interface TimelineDao {
	public List<Timeline> getTimeline(int teamNo);
	
	//chart
	
	// 팀별 오늘 작성된 칸반 카드 수 구하기
	public int countTodaysNewKanbanCards(int teamNo);
	
	
}
