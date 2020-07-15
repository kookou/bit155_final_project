package kr.or.bit3004.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import kr.or.bit3004.handler.ChattingHandler;
import kr.or.bit3004.handler.PaintHandler;


@Configuration
@EnableWebSocket //WebSocket 서버를 활성화하는 데 사용됨
public class WebSocketConfig implements WebSocketConfigurer{
	
	@Autowired
	PaintHandler socketPaintHandler;
	@Autowired
	ChattingHandler chattingHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(socketPaintHandler, "/paint");
		//도메인이 다른 서버에서도 접속 가능하도록 CORS : setAllowedOrigins(“*”)를 설정을 추가
		registry.addHandler(chattingHandler, "/chatting").setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor());
	}
	
}
