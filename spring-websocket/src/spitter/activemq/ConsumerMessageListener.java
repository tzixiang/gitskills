package spitter.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;

import spitter.entity.User;

/**
 * 消息监听器
 */
public class ConsumerMessageListener implements MessageListener {

	// 只需重写一个onMessage方法
	public void onMessage(Message message) {
		//TextMessage text = (TextMessage)message;
		ObjectMessage obj = (ObjectMessage) message;
		try {
			System.out.println("接收到一个对象消息: " + (User)obj.getObject());
			//System.out.println("接收到一个对象消息: " + text.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
