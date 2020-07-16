package kr.or.bit3004.handler;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import kr.or.bit3004.paint.PaintUser;

@Component("paint")
public class PaintHandler extends TextWebSocketHandler{

	private Map<String, List<PaintUser>> paintUserInfo = new HashMap<>();
	
	private List<WebSocketSession> connectedUsers;
	
	public PaintHandler() {
		connectedUsers = new ArrayList<WebSocketSession>();
	}
	
	//세션이 생성될 때 시작되는 함수
	@Override
	public synchronized void afterConnectionEstablished(WebSocketSession session)throws Exception {
		connectedUsers.add(session);
		//HttpSession 에서 사용자 정보 가져오기
		Map<String,Object> attrs= session.getAttributes();
		String id = (String)attrs.get("id");
		for (int i = 0; i < connectedUsers.size(); i++) {
			connectedUsers.get(i).sendMessage(new TextMessage("{ \"mode\" : \"fill\", \"color\" : \"#f0f0f0\" }"));
		}
	}
	
	public static boolean flag = false;
	
	//Client에서 메시지가 서버로 전송될 때 실행되는 함수.
	@Override
	protected synchronized void handleTextMessage(WebSocketSession session,TextMessage message)throws Exception{
	System.out.println("왜안타지ㅠㅠ");
		Map<String,Object> attrs = session.getAttributes();
		String id = (String)attrs.get("id");
		System.out.println("id한번찍어보기"+id);
		if(message.getPayload().equals("end")) {
			end(session);
		}
		for (WebSocketSession wss : connectedUsers) {
			if ( !wss.getId().equals(session.getId()) ) {
				wss.sendMessage(new TextMessage(message.getPayload()));
				System.out.println("handler에서보내는"+message.getPayload());
			}
		}
	}
	
	private void end(WebSocketSession session) throws InterruptedIOException{
		
	}
	
	//세션이 끝날때실행되는 함수
	@Override
	public synchronized void afterConnectionClosed(WebSocketSession session,CloseStatus stauts) throws Exception{
		Map<String,Object> attrs = session.getAttributes();
		String id = (String)attrs.get("id");
		connectedUsers.remove(session);
	}
}                 





