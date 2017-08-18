package spitter.socket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/pojo")
public class HelloEndpoint {
	
	@OnMessage
	public void onMessage(String message, Session session){
		System.out.println("���յ��������Ϣ��" + message);
	}
	
	@OnOpen
	public void onOpen(Session session){
		System.out.println("������! sessionId: "+session.getId());
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason){
		System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}
	
	@OnError
	public void onError(Throwable t){
		System.out.println("��������!");
		t.printStackTrace();
	}
}
