package spitter.socket;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * WebSocket��Ϣ������
 */
public class HelloSocketHandler extends AbstractWebSocketHandler {
	private ConcurrentHashMap<String, WebSocketSession> map = new ConcurrentHashMap<>();

	static{
		System.out.println("HelloScoketHandler");
	}
	
	// �رպ�
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		System.out.println("�ѹر�! Code: "+status.getCode()+"; Reason: "+status.getReason());
	}

	// ���Ӻ�
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		map.put("1001", session);
		System.out.println("Uri: "+session.getUri());
		System.out.println("������! id: "+session.getId());
	}

	// ������Ϣ
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		System.out.println("���������յ���Ϣ: "+message.getPayload());
		Thread.sleep(3000);
		session.sendMessage(new TextMessage("�Һܺ�("+session.getId()+")"));
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
