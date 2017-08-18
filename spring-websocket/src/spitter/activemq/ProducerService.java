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
 * ��Ϣ������
 */

@Component
public class ProducerService {
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	// ��sendMessage����������������ͨ��jmsTemplate��������Ϣ����Ӧ��Destination��
	public void sendMessage(Destination destination, final String message) {  
        System.out.println("---------------�����߷���һ����Ϣ��" + message);
        jmsTemplate.send(destination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createTextMessage(message);  
            }  
        });  
    }   
	
	public void sendUserMessage(Destination destination, User user) {  
        System.out.println("---------------�����߷���һ����Ϣ��" + user.toString());
        jmsTemplate.convertAndSend(destination, user);
        /*jmsTemplate.send(destination, new MessageCreator() {  
            public Message createMessage(Session session) throws JMSException {  
                return session.createObjectMessage(user);
            }  
        });*/  
    }  
}
