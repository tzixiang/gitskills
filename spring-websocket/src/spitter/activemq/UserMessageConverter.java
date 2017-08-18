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
 * 自定义消息转换器，实现MessageConverter接口
 */

@Component
public class UserMessageConverter implements MessageConverter {

	// 从消息中取出对象(消息  --> 对象)
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		ObjectMessage obj = (ObjectMessage) message;
		User user = (User) obj;
		return user;
	}

	// 将对象转换成消息
	public Message toMessage(Object obj, Session session) throws JMSException,
			MessageConversionException {
		ObjectMessage objectMessage = session.createObjectMessage();
		objectMessage.setObject((Serializable)obj);
		return objectMessage;
	}

}
