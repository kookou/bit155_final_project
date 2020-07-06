package kr.or.bit3004.handler;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component("paint/paint")
public class PaintHandler extends TextWebSocketHandler{

	//GamePaintHandler 따온거.
	
	private List<WebSocketSession> connectedUsers;
	
	public PaintHandler() {
		connectedUsers = new ArrayList<WebSocketSession>();
	}//안되면 초기자 비우고 전역에 list생성.
	
	//세션이 생성될 때 시작되는 함수
	@Override
	public synchronized void afterConnectionEstablished(WebSocketSession session)throws Exception {
		connectedUsers.add(session);
		Map<String,Object> attrs= session.getAttributes();
		String id = (String)attrs.get("id");
	}
	

	
	//GameChatHandler 따온거.


	public static boolean flag = false;
	
	//Client에서 메시지가 서버로 전송될 때 실행되는 함수.
	@Override
	protected synchronized void handleTextMessage(WebSocketSession session,TextMessage message)throws Exception{
		Map<String,Object> attrs = session.getAttributes();
		String id = (String)attrs.get("id");
		if(message.getPayload().equals("end")) {
			end(session);
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





