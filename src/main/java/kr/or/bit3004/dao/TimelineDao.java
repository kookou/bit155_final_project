package kr.or.bit3004.dao;

import java.util.List;

import kr.or.bit3004.timeline.Timeline;

public interface TimelineDao {
	public List<Timeline> getTimeline(int teamNo);
}
