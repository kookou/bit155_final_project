package kr.or.bit3004.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.bit3004.chatting.ChatUser;
import kr.or.bit3004.user.User;

@Component("chatting")
public class ChattingHandler extends TextWebSocketHandler {
	
	private Map<String, List<ChatUser>> chatUserInfo = new HashMap<>();
	
	public ChattingHandler() {}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// HttpSession 에서 사용자 정보 가져오기
		Map<String, Object> attrs = session.getAttributes();
		User currentUser = (User)attrs.get("currentUser");
		debug(currentUser.getNickname() + " 연결됨");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// HttpSession 객체에 설정된 정보를 가져온다.
		Map<String, Object> attrs = session.getAttributes();
		User currentUser = (User)attrs.get("currentUser");
		debug(currentUser.getNickname() + " 연결 종료됨");
		
		Set<String> keys = chatUserInfo.keySet();
		// 퇴장한 유저 찾기
		ChatUser removeUser = null;
		String userIncludeKey = "";
		for (String key : keys) {
			List<ChatUser> users = (List<ChatUser>)chatUserInfo.get(key);
			for(ChatUser user : users) {
				if(user.getSessionInfo().getId().equals(session.getId())) {
					removeUser = user;
					userIncludeKey = key;
					continue;
				}
			}
		}
		// 같은 방 사용자에게 퇴장 메세지를 전송한다. 단, 퇴장하는 유저는 제외
		// 메세지 전송 후 퇴장 유저를 방 목록에서 제거한다.
		List<ChatUser> users = chatUserInfo.get(userIncludeKey);
		for(ChatUser user : users) {
			if (user == removeUser) continue;
			WebSocketSession wSession = user.getSessionInfo();
			wSession.sendMessage(new TextMessage("notice∥"+removeUser.getTeamNo()+"∥"+currentUser.getNickname() + "∥님 접속종료∥" +time.format(new Date())));
		}
		users.remove(removeUser);
//		System.out.println("빠졌나"+chatUserInfo);
	}
	
	@Override
	public void handleTextMessage(WebSocketSession wss, TextMessage message) throws Exception {
		debug("보낸 아이디 - " + wss.getId());
		debug("보낸 메세지 - " + message.getPayload());
		
		String rcvMsg = message.getPayload();
		String sendMsg = "";
		
		String[] arr = rcvMsg.split("∥");
		String condition = arr[0]; 		  // 접속시 : connect / 메세지를 보내왔을때 : send
		String teamNo = arr[1]; 			  // 팀번호
		String userNickname = arr[2]; // 접속한 유저의 닉네임
		String msg = arr[3]; 				  // 메세지
		String image = arr[4];			  // 유저의 닉네임
		
		if (condition.equals("connent")) {
			if(!chatUserInfo.containsKey(teamNo)){
				List<ChatUser> users = new ArrayList<>();
				ChatUser user = new ChatUser();
				user.setNickname(userNickname);
				user.setSessionInfo(wss);
				user.setTeamNo(teamNo);
				users.add(user);
				chatUserInfo.put(teamNo, users);
			} else {
				ChatUser user = new ChatUser();
				user.setNickname(userNickname);
				user.setSessionInfo(wss);
				user.setTeamNo(teamNo);
				List<ChatUser> users = chatUserInfo.get(teamNo);
				users.add(user);
			}
			sendMsg = "notice"+"∥"+teamNo+"∥"+userNickname+"∥"+msg+"∥"+image+"∥"+time.format(new Date());
		} else {
			sendMsg = rcvMsg + "∥" +image+"∥"+time.format(new Date());
		}
		
//		System.out.println("sendMsg : " + sendMsg);
//		System.out.println("chatUserInfo : " + chatUserInfo);

		Set<String> keys = chatUserInfo.keySet();
		// 메세지가 전송된 방 찾기
		List<ChatUser> teamLiveUser = null;
		for (String key : keys) {
			if(key.equals(teamNo)) {
				teamLiveUser = chatUserInfo.get(key);
//				System.out.println("teamLiveUser: " + teamLiveUser);
			}
		}
		// 메세지가 전송된 방에만 메세지 뿌리기
		for(ChatUser user : teamLiveUser) {
			WebSocketSession wSession = user.getSessionInfo();
//			System.out.println("wSession: " + wSession);
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
