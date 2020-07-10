package kr.or.bit3004.groupAndTeam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamMember {
	private int teamNo;
	private String id;
	private String leader;
	private String nickname;
	private String image;
}
