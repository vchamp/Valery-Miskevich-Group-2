package com.alex.dzeshko;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	public static void main(String[] args) {
	      ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	      ByNameBean obj = (ByNameBean) context.getBean("byName");
	      ByTypeBean obj2 = context.getBean(ByTypeBean.class);
	      ByNameAndTypeBean obj3 = context.getBean("byNameAndType", ByNameAndTypeBean.class);
	      ByAliasBean obj4 = (ByAliasBean) context.getBean("alias");
	      
	      obj.getMessage();
	      obj2.getMessage();
	      obj3.getMessage();
	      obj4.getMessage();
	   }
}
