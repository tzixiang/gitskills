package spitter.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpitterWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{};
	}

	protected Class<?>[] getServletConfigClasses() {// 指定配置类
		return new Class<?>[]{WebConfig.class};
	}

	protected String[] getServletMappings() {// 映射路径
		return new String[]{"/"};// 所有请求
	}

}
