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
@EnableWebMvc// 启用Spring MVC
@ComponentScan("spitter")// 启用组件扫描
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver(){// 配置JSP视图解析器(Spring默认视图解析器)
		System.out.println("WebConfig");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/pages/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	// 配置静态资源的处理
	//当DisptacherServlet接收到了他匹配的请求，但是找不到相应的Controller，就会把这个请求返回给默认的处理（比如交给tomcat处理）
	public void configuraDefaultServletHandling(DefaultServletHandlerConfigurer config){
		config.enable();
	}
}
