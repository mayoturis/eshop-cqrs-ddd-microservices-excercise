package com.marekturis.common.infrastructure.messaging;

import com.marekturis.common.application.EventJsonSerializer;
import com.marekturis.common.domain.Event;
import com.marekturis.common.domain.EventHandler;
import com.marekturis.common.domain.EventPublisher;
import com.rabbitmq.client.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Marek Turis
 */
@Named
public class RabbitMQEventPublisher implements EventPublisher {

	private static final String EXCHANGE_NAME = "eshop";
	private Channel channel;
	private EventJsonSerializer serializer;

	@Inject
	public RabbitMQEventPublisher(EventJsonSerializer serializer) throws IOException, TimeoutException {
		this.serializer = serializer;

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
	}


	public void registerHandler(EventHandler handler) {
		try {
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, handler.eventToHandle());

			Consumer consumer = new RabbitMQConsumer(channel, handler);
			channel.basicConsume(queueName, consumer);
		} catch (IOException e) {
			throw new RabbitMQFailureException(e);
		}
	}

	public void publish(Event event) {
		String jsonEvent = serializer.fromEvent(event);
		try {
			channel.basicPublish(EXCHANGE_NAME, event.name(), null, jsonEvent.getBytes());
		} catch (IOException e) {
			throw new RabbitMQFailureException(e);
		}
	}

}
