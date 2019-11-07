package fr.excilys.db.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {SpringConfiguration.class};
	}
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
