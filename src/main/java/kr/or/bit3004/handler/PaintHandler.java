package kr.or.bit3004.handler;

import java.io.InterruptedIOException;
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

import kr.or.bit3004.paint.PaintUser;
import kr.or.bit3004.user.User;

@Component("paint")
public class PaintHandler extends TextWebSocketHandler{

	private Map<String, List<PaintUser>> paintUserInfo = new HashMap<>();
	
	private List<WebSocketSession> connectedUsers;
	
	public PaintHandler() {
		connectedUsers = new ArrayList<WebSocketSession>();
	}
	
	//세션이 생성될 때 시작되는 함수
	//즉 사용자 연결 이후에 실행되는 메소드
	@Override
	public synchronized void afterConnectionEstablished(WebSocketSession session)throws Exception {
//		System.out.println("안녕하세요!!! from paint");
//		connectedUsers.add(session);
//		//HttpSession 에서 사용자 정보 가져오기
//		Map<String,Object> attrs= session.getAttributes();
//		String id = (String)attrs.get("id");
//		for (int i = 0; i < connectedUsers.size(); i++) {
//			connectedUsers.get(i).sendMessage(new TextMessage("{ \"mode\" : \"fill\", \"color\" : \"#f0f0f0\" }"));
//		}
	}
	
	public static boolean flag = false;
	
	//Client에서 메시지가 웹소켓서버로 전송될 때 실행되는 함수.
	@Override
	protected synchronized void handleTextMessage(WebSocketSession session,TextMessage message)throws Exception{
		Map<String,Object> attrs = session.getAttributes();
		String id = (String)attrs.get("id");
		
		//  System.out.println("id한번찍어보기 "+id+" from paint");
		//  if(message.getPayload().equals("end")) { end(session); }
		
		/*
		 * for (WebSocketSession wss : connectedUsers) { if (
		 * !wss.getId().equals(session.getId()) ) { wss.sendMessage(new
		 * TextMessage(message.getPayload()));
		 * System.out.println("handler에서보내는"+message.getPayload()); } }
		 */
		//위 서영 원래꺼
		String rcvMsg = message.getPayload();
		System.out.println("rcvMsg: "+rcvMsg);
		String[] arr = rcvMsg.split("-");
		String condition = arr[0];
		String teamNo = arr[1];
		String userNickname = arr[2];
		String msg = arr[3];
		//유저 접속시
		if(condition.equals("connect")) {
			if(!paintUserInfo.containsKey(teamNo)) {
				List<PaintUser> users = new ArrayList<>();
				PaintUser user = new PaintUser();
				user.setNickname(userNickname);
				user.setSessionInfo(session);
				user.setTeamNo(teamNo);
				users.add(user);
				paintUserInfo.put(teamNo,users);
				System.out.println(paintUserInfo);
			} else {
				PaintUser user = new PaintUser();
				user.setNickname(userNickname);
				user.setSessionInfo(session);
				user.setTeamNo(teamNo);
				List<PaintUser> users = paintUserInfo.get(teamNo);
				users.add(user);
				System.out.println(paintUserInfo);
			}
		} 
		//그림 json이 왔을 경우
		else if(condition.equals("send")) {
			System.out.println("아니면 이걸탔다.");
			
			//메시지가 전송된 방 찾기
			Set<String>keys = paintUserInfo.keySet();
			List<PaintUser> teamLiveUser = null;
			for(String key: keys) {
				System.out.println("팀넘"+teamNo);
				System.out.println("이 위로는 타는데 이 밑으로 안탄다.");
				if(key.equals(teamNo)) {
					teamLiveUser = paintUserInfo.get(key);
					System.out.println("teamLiveUser: "+teamLiveUser+" from paint");
				}
			}
			//메세지가 전송될 방에만 메시지 뿌리기
			for(PaintUser user: teamLiveUser) {
				WebSocketSession wSession = user.getSessionInfo();
				System.out.println("wSession: "+wSession +" from paint");
				wSession.sendMessage(new TextMessage(msg));
			}
		}

		
	}
	
	private void end(WebSocketSession session) throws InterruptedIOException{
		
	}
	
	//세션이 끝날때실행되는 함수
	@Override
	public synchronized void afterConnectionClosed(WebSocketSession session,CloseStatus stauts) throws Exception{
//		Map<String,Object> attrs = session.getAttributes();
//		/* String id = (String)attrs.get("id"); //서영원래있떤거*/
//		User currentUser = (User)attrs.get("currentUser"); //유저를 맵에 넣기
//		debug(currentUser.getNickname()+"연결이 종료됨 from paint");
		
		Set<String> keys = paintUserInfo.keySet(); //paintuserinfo map의 key값 받아오기
		//퇴장한 유저 찾기
		PaintUser removeUser = null;
		String userIncludeKey = ""; //뭐지?
		for(String key: keys) {
			List<PaintUser> users = (List<PaintUser>)paintUserInfo.get(key);
			for(PaintUser user : users) {
				if(user.getSessionInfo().getId().equals(session.getId())) {
					removeUser = user;
					userIncludeKey = key;
					continue;
				}
			}
		}
		List<PaintUser> users = paintUserInfo.get(userIncludeKey);
//		for(PaintUser user : users) {
//			if(user == removeUser) continue;
//			WebSocketSession wSession = user.getSessionInfo();
//			wSession.sendMessage(new TextMessage("notice-"+removeUser.getTeamNo()+"-"+currentUser.getNickname()+"-님 접속종료 from paint"));
//		}
		users.remove(removeUser);
		System.out.println("페인트유저빠졌나"+paintUserInfo);
//		connectedUsers.remove(session);//서영원래있떤거
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		debug(session.getId() + " exception 발생 - " + exception.getMessage()+"from paint");
	}
	private void debug(String msg) {
		System.out.printf(this.getClass().getSimpleName() + "(%s) : %s\n", msg);
	}
}                 





