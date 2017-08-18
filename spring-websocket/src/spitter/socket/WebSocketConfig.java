package spitter.socket;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket×¢²á
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	static{
		System.out.println("WebSocketConfig");
	}
	
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("WebSocketConfig.registry");
		registry.addHandler(helloSocketHandler(), "/websocket/demo");
	}

	@Bean
	@Qualifier("socketHandler")
	public HelloSocketHandler helloSocketHandler(){
		return new HelloSocketHandler();
	}
}
