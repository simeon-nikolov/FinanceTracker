package dao;

import model.FinanceOperationType;
import model.Tag;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Demo {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AutowiredConfiguration.class);
		ITagDAO dao = context.getBean(TagDAO.class);
		dao.addTag(new Tag(1, "Work", FinanceOperationType.INCOME));
	}

}
