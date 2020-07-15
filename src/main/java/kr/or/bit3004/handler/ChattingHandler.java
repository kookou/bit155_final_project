package kr.or.bit3004.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.bit3004.user.User;

@Component("chatting")
public class ChattingHandler extends TextWebSocketHandler {
	
	private Map<String, WebSocketSession> users = new HashMap<>();
	
	public ChattingHandler() {
		System.out.println(this.getClass().getSimpleName() + " 객체 생성");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// HttpSession 에서 사용자 정보 가져오기
		Map<String, Object> attrs = session.getAttributes();
		User currentUser = (User)attrs.get("currentUser");
		users.put(currentUser.getNickname(), session);
		debug(currentUser.getNickname() + " 연결됨");
		Set<String> keys = users.keySet();
		for (String key : keys) {
			WebSocketSession wSession = users.get(key);
			wSession.sendMessage(
				new TextMessage("notice∥"+currentUser.getNickname()+"님이 접속하였습니다.∥"+time.format(new Date()))
			);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// HttpSession 객체에 설정된 정보를 가져온다.
		Map<String, Object> attrs = session.getAttributes();
		User currentUser = (User)attrs.get("currentUser");
		users.remove(currentUser.getNickname());
		debug(currentUser.getNickname() + " 연결 종료됨");
		System.out.println(users);
		Set<String> keys = users.keySet();
		for (String key : keys) {
			WebSocketSession wSession = users.get(key);
			wSession.sendMessage(
				new TextMessage("notice∥"+currentUser.getNickname()+"님이 접속 종료하였습니다.∥"+time.format(new Date()))
			);
		}
	}
	
	@Override
	public void handleTextMessage(WebSocketSession wss, TextMessage message) throws Exception {
		debug("보낸 아이디 - " + wss.getId());
		debug("보낸 메세지 - " + message.getPayload());
		
		String rcvMsg = message.getPayload();
		String sendMsg = "";
		sendMsg = rcvMsg + "∥" + time.format(new Date());
		
		System.out.println("sendMsg : " + sendMsg);
		System.out.println("users : " + users);

		Set<String> keys = users.keySet();
		for (String key : keys) {
			WebSocketSession wSession = users.get(key);
			wSession.sendMessage(new TextMessage(sendMsg));
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		debug(session.getId() + " 익셉션 발생 - " + exception.getMessage());
	}
	
	private SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
	
	private void debug(String msg) {
		System.out.printf(this.getClass().getSimpleName() + "(%s) : %s\n", time.format(new Date()), msg);
	}
	
}
