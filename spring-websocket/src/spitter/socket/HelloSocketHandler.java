package spitter.socket;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * WebSocket消息处理器
 */
public class HelloSocketHandler extends AbstractWebSocketHandler {
	private ConcurrentHashMap<String, WebSocketSession> map = new ConcurrentHashMap<>();

	static{
		System.out.println("HelloScoketHandler");
	}
	
	// 关闭后
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		System.out.println("已关闭! Code: "+status.getCode()+"; Reason: "+status.getReason());
	}

	// 连接后
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		map.put("1001", session);
		System.out.println("Uri: "+session.getUri());
		System.out.println("已连接! id: "+session.getId());
	}

	// 处理消息
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		System.out.println("服务器接收到消息: "+message.getPayload());
		Thread.sleep(3000);
		session.sendMessage(new TextMessage("我很好("+session.getId()+")"));
	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		exception.printStackTrace();
	}

	public ConcurrentHashMap<String, WebSocketSession> getMap() {
		return map;
	}
	public void setMap(ConcurrentHashMap<String, WebSocketSession> map) {
		this.map = map;
	}
}
