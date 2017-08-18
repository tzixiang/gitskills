package spitter.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import spitter.entity.User;

/**
 * �Զ�����Ϣת������ʵ��MessageConverter�ӿ�
 */

@Component
public class UserMessageConverter implements MessageConverter {

	// ����Ϣ��ȡ������(��Ϣ  --> ����)
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		ObjectMessage obj = (ObjectMessage) message;
		User user = (User) obj;
		return user;
	}

	// ������ת������Ϣ
	public Message toMessage(Object obj, Session session) throws JMSException,
			MessageConversionException {
		ObjectMessage objectMessage = session.createObjectMessage();
		objectMessage.setObject((Serializable)obj);
		return objectMessage;
	}

}
