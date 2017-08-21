package spitter.activemq;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * ��Ӧ��xml�����ļ���spring-activemq.xml
 */
@Configuration
@ComponentScan
public class ActiveMQConfig {

	// SingleConnectionFactory��CacheConnectionFactory��Spring�ṩ���ڹ���ActiveMQ
	@Bean
	public SingleConnectionFactory connectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
		return new SingleConnectionFactory(activeMQConnectionFactory);
	}
	
	// ������������ActiveMQ��ConnectionFactory(����)
	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory(){
		ActiveMQConnectionFactory activeMQ = new ActiveMQConnectionFactory("tcp://localhost:61616");
		activeMQ.setTrustAllPackages(true);// �����û��رհ�ȫ��飬�����������ࡣ�����ڲ���Ŀ�ĺ�����
		//activeMQ.setTrustedPackages(new ArrayList<String>(
		//		Arrays.asList("spitter.entity.User,spitter.entity.Company".split(","))));
		// ����ָ������������
		return activeMQ;
	}
	
	//ActiveMQΪ�����ṩ��һ��PooledConnectionFactory��ͨ��������ע��һ��ActiveMQConnectionFactory
	//����������Connection��Session��MessageProducer�ػ����������Դ��ļ������ǵ���Դ���ġ�
	@Bean
	public PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
		PooledConnectionFactory pcf = new PooledConnectionFactory(activeMQConnectionFactory);
		pcf.setMaxConnections(10);
		return pcf;
	}
	
	// Spring�ṩ��JMS�����࣬�����Խ�����Ϣ���͡����յ�
	@Bean
	public JmsTemplate jmsTemplate(SingleConnectionFactory connectionFactory,UserMessageConverter messageConverter){
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(messageConverter);// ������Ϣת����
		return jmsTemplate;
		//return new JmsTemplate(connectionFactory);
	}
	
	//------------------------------------------------------------------------------//
	
	/**
	 * ����������JmsTemplate������Ϣ���͵�ʱ��������Ҫ֪����Ϣ���͵�Ŀ�ĵأ���destination��
	 * ��Jms����һ��������ʾĿ�ĵص�Destination�ӿڣ�������û���κη������壬ֻ��������һ����ʶ���ѡ�
	 * ��������ʹ��JmsTemplate������Ϣ����ʱû��ָ��destination��ʱ��ʹ��Ĭ�ϵ�Destination��
	 * Ĭ��Destination����ͨ���ڶ���jmsTemplate bean����ʱͨ������defaultDestination��
	 * defaultDestinationName������ע�룬defaultDestinationName��Ӧ�ľ���һ����ͨ�ַ�����
	 * ��ActiveMQ��ʵ�����������͵�Destination��һ���ǵ�Ե��ActiveMQQueue��
	 * ��һ������֧�ֶ���/����ģʽ��ActiveMQTopic���ڶ������������͵�Destinationʱ���Ƕ�����ͨ��
	 * һ��name���������й���
	 */
	
	// ����Ƕ���Ŀ�ĵأ���Ե��
	@Bean
	public ActiveMQQueue activeMQQueue(){
		return new ActiveMQQueue("queue");
	}
	
	// ���������Ŀ�ĵأ�һ�Զ��
	@Bean
	public ActiveMQTopic activeMQTopic(){
		return new ActiveMQTopic("topic");
	}
	
	/**
	 * ͨ��SpringΪ���Ƿ�װ����Ϣ��������MessageListenerContainerʵ�ֵģ������������Ϣ��
	 * ���ѽ��յ�����Ϣ�ַ���������MessageListener���д���ÿ�������߶�Ӧÿ��Ŀ�ĵض���Ҫ�ж�Ӧ��
	 * MessageListenerContainer��Springһ��Ϊ�����ṩ���������͵�MessageListenerContainer:
	 * SimpleMessageListenerContainer��DefaultMessageListenerContainer��
	 * SimpleMessageListenerContainer����һ��ʼ��ʱ��ʹ���һ���Ựsession��������Consumer��
	 * ���һ�ʹ�ñ�׼��JMS MessageConsumer.setMessageListener()����ע���������JMS�ṩ�ߵ��ü�����
	 * �Ļص������������ᶯ̬����Ӧ����ʱ��Ҫ�Ͳ����ⲿ��������������Է��棬���ǳ��ӽ��ڶ�����JMS�淶��
	 * ��һ�㲻����Java EE��JMS���ơ��������������ǻ���ʹ�õ�DefaultMessageListenerContainer��
	 * ��SimpleMessageListenerContainer��ȣ�DefaultMessageListenerContainer�ᶯ̬����Ӧ����ʱ
	 * ��Ҫ�������ܹ������ⲿ������������ܺõ�ƽ���˶�JMS�ṩ��Ҫ��͡��Ƚ��������������ͼ���Java EE������
	 */
	
	// ��Ϣ������
	@Bean
	public ConsumerMessageListener messageListener(){
		return new ConsumerMessageListener();
	}
	
	// ��Ϣ��������
	@Bean
	public DefaultMessageListenerContainer messageListenerContainer(//ActiveMQQueue activeMQQueue,
			SingleConnectionFactory connectionFactory,ConsumerMessageListener messageListener){
		DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
		listener.setConnectionFactory(connectionFactory);// ����Spring��ConnectionFactory(����������)
		//listener.setDestination(activeMQQueue);
		listener.setDestinationName("queue");// ����Ŀ�ĵ�(������Ŀ�ĵ�)
		listener.setMessageListener(messageListener);// ���ü�����
		return listener;
	}
}
