package dao;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("dao")
public class SpringWebConfiguration {
	@Bean
	public SessionFactory sessionFactory() {
		return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
	}
}
