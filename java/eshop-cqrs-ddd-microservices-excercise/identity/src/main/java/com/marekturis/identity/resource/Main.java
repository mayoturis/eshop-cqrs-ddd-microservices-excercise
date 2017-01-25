package com.marekturis.identity.resource;

/**
 * @author Marek Turis
 */
public class Main {

	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
		//ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
		//UserService userService = context.getBean("userService", UserService.class);


		//userService.addUser("marek.turis@gmail.com", "marek2", "turis", "password");
		Nieco nieco = new Nieco();
		Nieco nieco1 = new NiecoIne();
		System.out.println(nieco.getClassName());
		System.out.println(nieco1.getClassName());
	}

	public static class Nieco {
		public String getClassName() {
			return this.getClass().getSimpleName();
		}
	}

	public static class NiecoIne extends Nieco {

	}
}
