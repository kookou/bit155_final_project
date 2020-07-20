package kr.or.bit3004.user;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser extends User implements Serializable{
	private String id;
	private String nickname;
	private String image;
	
	//팀페이지 안에서 필요한 정보는 팀페이지 접근시 setter로 주입
	private int teamNo;
	private String isTeamLeader;
	
	
	public SessionUser(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.image = user.getImage();
	}

	//teamNo, teamLeader만 setter로 주입가능
	public void setTeamNo(int teamNo) {
		this.teamNo = teamNo;
	}

	public void setIsTeamLeader(String isTeamLeader) {
		this.isTeamLeader = isTeamLeader;
	}

	
	
	
	


}
