package config;

import javax.servlet.Filter;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.
AbstractAnnotationConfigDispatcherServletInitializer;
 
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringWebConfig.class, SecurityConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "*.html", "*.pdf" };
    }
    
    @Override
    protected Filter[] getServletFilters() {
       return new Filter[]{ 
    		   new DelegatingFilterProxy("springSecurityFilterChain")};
    } 
}