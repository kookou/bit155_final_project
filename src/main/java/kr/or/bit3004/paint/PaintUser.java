package kr.or.bit3004.paint;

import org.springframework.web.socket.WebSocketSession;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaintUser {
	private String nickname;
	private WebSocketSession sessionInfo;
	private String teamNo;
	//paintUserInfo Map의 value값이 될 녀석들.... key는 teamNo
}
