package spitter.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;

import spitter.entity.User;

/**
 * ��Ϣ������
 */
public class ConsumerMessageListener implements MessageListener {

	// ֻ����дһ��onMessage����
	public void onMessage(Message message) {
		//TextMessage text = (TextMessage)message;
		ObjectMessage obj = (ObjectMessage) message;
		try {
			System.out.println("���յ�һ��������Ϣ: " + (User)obj.getObject());
			//System.out.println("���յ�һ��������Ϣ: " + text.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
