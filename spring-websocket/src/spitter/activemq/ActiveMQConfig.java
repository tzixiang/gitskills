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
 * 对应的xml配置文件是spring-activemq.xml
 */
@Configuration
@ComponentScan
public class ActiveMQConfig {

	// SingleConnectionFactory与CacheConnectionFactory是Spring提供用于管理ActiveMQ
	@Bean
	public SingleConnectionFactory connectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
		return new SingleConnectionFactory(activeMQConnectionFactory);
	}
	
	// 真正可以连接ActiveMQ的ConnectionFactory(厂商)
	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory(){
		ActiveMQConnectionFactory activeMQ = new ActiveMQConnectionFactory("tcp://localhost:61616");
		activeMQ.setTrustAllPackages(true);// 允许用户关闭安全检查，并相信所有类。它对于测试目的很有用
		//activeMQ.setTrustedPackages(new ArrayList<String>(
		//		Arrays.asList("spitter.entity.User,spitter.entity.Company".split(","))));
		// 允许指定包都被信任
		return activeMQ;
	}
	
	//ActiveMQ为我们提供了一个PooledConnectionFactory，通过往里面注入一个ActiveMQConnectionFactory
	//可以用来将Connection、Session和MessageProducer池化，这样可以大大的减少我们的资源消耗。
	@Bean
	public PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
		PooledConnectionFactory pcf = new PooledConnectionFactory(activeMQConnectionFactory);
		pcf.setMaxConnections(10);
		return pcf;
	}
	
	// Spring提供的JMS工具类，它可以进行消息发送、接收等
	@Bean
	public JmsTemplate jmsTemplate(SingleConnectionFactory connectionFactory,UserMessageConverter messageConverter){
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(messageConverter);// 设置消息转换器
		return jmsTemplate;
		//return new JmsTemplate(connectionFactory);
	}
	
	//------------------------------------------------------------------------------//
	
	/**
	 * 在真正利用JmsTemplate进行消息发送的时候，我们需要知道消息发送的目的地，即destination。
	 * 在Jms中有一个用来表示目的地的Destination接口，它里面没有任何方法定义，只是用来做一个标识而已。
	 * 当我们在使用JmsTemplate进行消息发送时没有指定destination的时候将使用默认的Destination。
	 * 默认Destination可以通过在定义jmsTemplate bean对象时通过属性defaultDestination或
	 * defaultDestinationName来进行注入，defaultDestinationName对应的就是一个普通字符串。
	 * 在ActiveMQ中实现了两种类型的Destination，一个是点对点的ActiveMQQueue，
	 * 另一个就是支持订阅/发布模式的ActiveMQTopic。在定义这两种类型的Destination时我们都可以通过
	 * 一个name属性来进行构造
	 */
	
	// 这个是队列目的地，点对点的
	@Bean
	public ActiveMQQueue activeMQQueue(){
		return new ActiveMQQueue("queue");
	}
	
	// 这个是主题目的地，一对多的
	@Bean
	public ActiveMQTopic activeMQTopic(){
		return new ActiveMQTopic("topic");
	}
	
	/**
	 * 通过Spring为我们封装的消息监听容器MessageListenerContainer实现的，它负责接收信息，
	 * 并把接收到的信息分发给真正的MessageListener进行处理。每个消费者对应每个目的地都需要有对应的
	 * MessageListenerContainer。Spring一共为我们提供了两种类型的MessageListenerContainer:
	 * SimpleMessageListenerContainer和DefaultMessageListenerContainer。
	 * SimpleMessageListenerContainer会在一开始的时候就创建一个会话session和消费者Consumer，
	 * 并且会使用标准的JMS MessageConsumer.setMessageListener()方法注册监听器让JMS提供者调用监听器
	 * 的回调函数。它不会动态的适应运行时需要和参与外部的事务管理。兼容性方面，它非常接近于独立的JMS规范，
	 * 但一般不兼容Java EE的JMS限制。大多数情况下我们还是使用的DefaultMessageListenerContainer，
	 * 跟SimpleMessageListenerContainer相比，DefaultMessageListenerContainer会动态的适应运行时
	 * 需要，并且能够参与外部的事务管理。它很好的平衡了对JMS提供者要求低、先进功能如事务参与和兼容Java EE环境。
	 */
	
	// 消息监听器
	@Bean
	public ConsumerMessageListener messageListener(){
		return new ConsumerMessageListener();
	}
	
	// 消息监听容器
	@Bean
	public DefaultMessageListenerContainer messageListenerContainer(//ActiveMQQueue activeMQQueue,
			SingleConnectionFactory connectionFactory,ConsumerMessageListener messageListener){
		DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
		listener.setConnectionFactory(connectionFactory);// 设置Spring的ConnectionFactory(监听的连接)
		//listener.setDestination(activeMQQueue);
		listener.setDestinationName("queue");// 设置目的地(监听的目的地)
		listener.setMessageListener(messageListener);// 设置监听器
		return listener;
	}
}
