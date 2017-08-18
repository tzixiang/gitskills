package spitter.web;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import spitter.socket.HelloSocketHandler;

@Controller
public class HomeController {
	
	@Resource
	private HelloSocketHandler socketHandler;
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String home() throws IOException{
		ConcurrentHashMap<String, WebSocketSession> map = socketHandler.getMap();
		WebSocketSession session = map.get("1001");
		session.sendMessage(new TextMessage("³É¹¦!"));
		System.out.println("home");
		return "home";
	}
}
