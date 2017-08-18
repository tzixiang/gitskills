package spitter.mail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HelloMailSender.class)
public class MailTest {
	@Resource
	private JavaMailSender mailSender;
	
	/*@Resource
	private VelocityEngine velocityEngine;*/
	
	// 发送简单文本email
	@Test
	public void sendMail() throws RuntimeException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("854201131@qq.com");
		message.setTo("qiuchengruishige@163.com");
		message.setSubject("你好");
		message.setText("收到请回!");
		mailSender.send(message);
		System.out.println("发送成功!");
	}
	
	// 发送带附件的email
	@Test
	public void sendMailPNG() throws MessagingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("854201131@qq.com");
		helper.setTo("qiuchengruishige@163.com");
		helper.setSubject("你好");
		helper.setText("收到请回!");
		FileSystemResource res = new FileSystemResource("src/spitter/abc.jpg");
		helper.addAttachment("图1.jpg", res);// 添加附件
		mailSender.send(message);
		System.out.println("发送成功!");
	}
	
	// 发送带html的email
	@Test
	public void sendMailHTML() throws MessagingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("854201131@qq.com");
		helper.setTo("qiuchengruishige@163.com");
		helper.setSubject("你好");
		helper.setText("<html><body><img src='cid:Logo'/>"
				+ "<h4>Logo</h4></body></html>", true);// 表示文本为html
		helper.addInline("Logo", new FileSystemResource("src/spitter/abc.jpg"));
		mailSender.send(message);
		System.out.println("发送成功!");
	}
	
	// 使用Velocity模板发送HTML的email
	/*@SuppressWarnings("deprecation")
	@Test
	public void sendMailVelocity() throws Exception{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("854201131@qq.com");
		helper.setTo("qiuchengruishige@163.com");
		helper.setSubject("你好");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", "邱程瑞");
		map.put("value", "24");
		helper.addInline("Logo", new FileSystemResource("src/spitter/abc.jpg"));
		String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
				"src/emailVelocity.vm", map);
		helper.setText(html, true);
		mailSender.send(message);
		System.out.println("发送成功!");
	}*/
}
