package spitter.mail;

import java.util.Properties;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

/**
 * Spring Mail
 */

@Configuration
@PropertySource("classpath:spitter/mail.properties")
public class HelloMailSender {
	
	@Bean
	public MailSender mailSender(Environment env){
		JavaMailSenderImpl mail = new JavaMailSenderImpl();
		mail.getSession().setDebug(true);// 打印发送日志，很关键
		mail.setHost(env.getProperty("host"));
		mail.setPort(Integer.parseInt(env.getProperty("port")));
		mail.setUsername(env.getProperty("user"));
		mail.setPassword(env.getProperty("password"));
		return mail;
	}
	
	// 缺少jar包暂时不使用
	// 使用Velocity模板发送HTML的email(转换html为string)
	/*@Bean
	public VelocityEngineFactoryBean velocityEngine(){
		VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
		Properties pro = new Properties();
		pro.setProperty("resource.loader", "classpath");
		pro.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.setVelocityProperties(pro);
		return velocityEngine;
	}*/
	
}
