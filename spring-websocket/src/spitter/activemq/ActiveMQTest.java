package spitter.activemq;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spitter.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ActiveMQConfig.class)
public class ActiveMQTest {
	
	@Resource
	private ProducerService producerService;
	
	@Resource
	@Qualifier("activeMQQueue")
	private Destination destination;
	
	@Test
	public void testSend(){ 
        for (int i=0; i<2; i++) {
            producerService.sendMessage(destination, "你好，生产者！这是消息：qcr" + (i+1));  
        }  
    }
	
	@Test
	public void testSendUser(){ 
        for (int i=0; i<2; i++) {
        	User u = new User(""+i,"邱程瑞"+i,i);
            producerService.sendUserMessage(destination, u);  
        }  
    }
}
