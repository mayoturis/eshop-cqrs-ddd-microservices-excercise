package com.marekturis.identity.resource;

import com.marekturis.identity.application.UserService;
import com.marekturis.identity.domain.role.Role;
import com.marekturis.identity.domain.user.FullName;
import com.marekturis.identity.domain.user.Person;
import com.marekturis.identity.domain.user.User;
import com.marekturis.identity.infrastructure.ApplicationContextConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Marek Turis
 */
public class Main {

	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
		//ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
		//UserService userService = context.getBean("userService", UserService.class);


		//userService.addUser("marek.turis@gmail.com", "marek2", "turis", "password");
	}
}
