package spitter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc// ����Spring MVC
@ComponentScan("spitter")// �������ɨ��
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver(){// ����JSP��ͼ������(SpringĬ����ͼ������)
		System.out.println("WebConfig");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/pages/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	// ���þ�̬��Դ�Ĵ���
	//��DisptacherServlet���յ�����ƥ������󣬵����Ҳ�����Ӧ��Controller���ͻ��������󷵻ظ�Ĭ�ϵĴ������罻��tomcat����
	public void configuraDefaultServletHandling(DefaultServletHandlerConfigurer config){
		config.enable();
	}
}
