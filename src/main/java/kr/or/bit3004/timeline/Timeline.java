package kr.or.bit3004.timeline;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Timeline {
	private int timelineNo;
	private String columnName;
	private String columnNo;
	private int teamNo;
	private String id;
	private String dmlKind;
	private String oldHistory;
	private String history;
	private String historyTime;
	private String dmlName;
	private String nickname;
	private String image;
}
