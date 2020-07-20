package kr.or.bit3004.user;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser extends User implements Serializable{
	private String id;
	private String nickname;
	private String image;
	
	public SessionUser(User user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.image = user.getImage();
	}

}
