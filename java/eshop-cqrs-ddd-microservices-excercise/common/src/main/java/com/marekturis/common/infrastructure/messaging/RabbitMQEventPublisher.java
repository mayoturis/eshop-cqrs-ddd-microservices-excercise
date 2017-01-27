package com.marekturis.common.infrastructure.messaging;

import com.marekturis.common.application.EventJsonSerializer;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.EventPublisher;
import com.rabbitmq.client.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Marek Turis
 */
@Named
public class RabbitMQEventPublisher implements EventPublisher, AutoCloseable {

	private static final String EXCHANGE_NAME = "eshop_events";
	private final Connection connection;
	private Channel channel;
	private EventJsonSerializer serializer;

	@Inject
	public RabbitMQEventPublisher(EventJsonSerializer serializer) throws IOException, TimeoutException {
		this.serializer = serializer;

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
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

	public void close() throws IOException, TimeoutException {
		if (channel != null && channel.isOpen()) {
			channel.close();
		}

		if (connection != null && connection.isOpen()) {
			connection.close();
		}
	}
}
