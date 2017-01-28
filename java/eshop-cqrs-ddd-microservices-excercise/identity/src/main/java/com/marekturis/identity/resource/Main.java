package com.marekturis.identity.resource;

import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.JacksonEventJsonSerializer;
import com.marekturis.common.infrastructure.messaging.RabbitMQEventPublisher;
import com.marekturis.identity.infrastructure.IdentityConfig;
import com.marekturis.identity.resource.config.RestApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

/**
 * @author Marek Turis
 */
public class Main {

	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
		ApplicationContext context = new AnnotationConfigApplicationContext(IdentityConfig.class);
		//UserService userService = context.getBean("userService", UserService.class);

		EventPublisher eventPublisher = context.getBean(EventPublisher.class);

		Event event = new SomeEvent();
		EventHandler handler = new SomeEventHandler();
		//eventPublisher.registerHandler(handler);
		eventPublisher.publish(event);
	}

	public static class SomeEvent implements Event {

		private Date occuredOn = new Date();
		private String name = "ProductCreated";
		private int nieco = 5;

		public String identity() { return "d"; }

		public Date occuredOn() {
			return new Date();
		}

		public String name() {
			return name;
		}

		public int version() {
			return 0;
		}
	}

	public static class SomeEventHandler implements EventHandler {

		public String eventToHandle() {
			return "namo";
		}

		public void handle(ParsableEvent event) {
			System.out.println(event.occuredOn());
			System.out.println(event.name());
		}
	}
}
