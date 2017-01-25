package com.marekturis.common;

import com.marekturis.common.application.EventJsonSerializer;
import com.marekturis.common.domain.Event;
import com.marekturis.common.domain.EventHandler;
import com.marekturis.common.domain.EventPublisher;
import com.marekturis.common.domain.ParsableEvent;
import com.marekturis.common.infrastructure.JacksonEventJsonSerializer;
import com.marekturis.common.infrastructure.messaging.RabbitMQEventPublisher;

import java.util.Date;

/**
 * @author Marek Turis
 */
public class Main {

	public static void main(String[] args) throws Exception {
		EventPublisher eventPublisher = new RabbitMQEventPublisher(new JacksonEventJsonSerializer());
		Event event = new SomeEvent();
		EventHandler handler = new SomeEventHandler();
		eventPublisher.registerHandler(handler);
		eventPublisher.publish(event);
		System.in.read();
		System.in.read();
	}

	public static class SomeEvent implements Event {

		private Date date = new Date();
		private String name = "namo";

		public Date occuredOn() {
			return new Date();
		}

		public String name() {
			return name;
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
