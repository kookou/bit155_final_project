package kr.or.bit3004.timeline;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Timeline {
	private int timelineNo;
	private String action;
	private String field;
	private int teamNo;
	private String id;
	private int dmlNo;
	private String dmlName;
}
