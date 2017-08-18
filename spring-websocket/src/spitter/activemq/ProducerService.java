package spitter.activemq;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import spitter.entity.User;

/**
 * 消息生产者
 */

@Component
public class ProducerService {
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	// 在sendMessage方法体里面我们是通过jmsTemplate来发送消息到对应的Destination的
	public void sendMessage(Destination destination, final String message) {  
        System.out.println("---------------生产者发了一个消息：" + message);
        jmsTemplate.send(destination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createTextMessage(message);  
            }  
        });  
    }   
	
	public void sendUserMessage(Destination destination, User user) {  
        System.out.println("---------------生产者发了一个消息：" + user.toString());
        jmsTemplate.convertAndSend(destination, user);
        /*jmsTemplate.send(destination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createObjectMessage(user);
            }  
        });*/  
    }  
}
