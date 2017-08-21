package spitter.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.activemq.web.AjaxServlet;
import org.springframework.web.WebApplicationInitializer;

/**
 * ×¢²áServlet¡¢Filter¡¢Listener
 */
public class ServletConfig implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("ServletConfig");
		servletContext.addServlet("DynamicServlet", AjaxServlet.class);
	}

}
