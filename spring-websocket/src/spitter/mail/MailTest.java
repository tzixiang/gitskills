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
	
	// ���ͼ��ı�email
	@Test
	public void sendMail() throws RuntimeException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("854201131@qq.com");
		message.setTo("qiuchengruishige@163.com");
		message.setSubject("���");
		message.setText("�յ����!");
		mailSender.send(message);
		System.out.println("���ͳɹ�!");
	}
	
	// ���ʹ�������email
	@Test
	public void sendMailPNG() throws MessagingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("854201131@qq.com");
		helper.setTo("qiuchengruishige@163.com");
		helper.setSubject("���");
		helper.setText("�յ����!");
		FileSystemResource res = new FileSystemResource("src/spitter/abc.jpg");
		helper.addAttachment("ͼ1.jpg", res);// ��Ӹ���
		mailSender.send(message);
		System.out.println("���ͳɹ�!");
	}
	
	// ���ʹ�html��email
	@Test
	public void sendMailHTML() throws MessagingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("854201131@qq.com");
		helper.setTo("qiuchengruishige@163.com");
		helper.setSubject("���");
		helper.setText("<html><body><img src='cid:Logo'/>"
				+ "<h4>Logo</h4></body></html>", true);// ��ʾ�ı�Ϊhtml
		helper.addInline("Logo", new FileSystemResource("src/spitter/abc.jpg"));
		mailSender.send(message);
		System.out.println("���ͳɹ�!");
	}
	
	// ʹ��Velocityģ�巢��HTML��email
	/*@SuppressWarnings("deprecation")
	@Test
	public void sendMailVelocity() throws Exception{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("854201131@qq.com");
		helper.setTo("qiuchengruishige@163.com");
		helper.setSubject("���");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", "�����");
		map.put("value", "24");
		helper.addInline("Logo", new FileSystemResource("src/spitter/abc.jpg"));
		String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
				"src/emailVelocity.vm", map);
		helper.setText(html, true);
		mailSender.send(message);
		System.out.println("���ͳɹ�!");
	}*/
}
