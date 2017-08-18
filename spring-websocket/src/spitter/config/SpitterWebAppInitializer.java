package spitter.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpitterWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{};
	}

	protected Class<?>[] getServletConfigClasses() {// ָ��������
		return new Class<?>[]{WebConfig.class};
	}

	protected String[] getServletMappings() {// ӳ��·��
		return new String[]{"/"};// ��������
	}

}
