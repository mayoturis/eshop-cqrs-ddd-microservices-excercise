package com.marekturis.common.infrastructure.messaging;

import com.marekturis.common.domain.EventHandler;
import com.marekturis.common.domain.JsonParsableEvent;
import com.marekturis.common.domain.ParsableEvent;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author Marek Turis
 */
public class RabbitMQConsumer extends DefaultConsumer {

	EventHandler handler;

	public RabbitMQConsumer(Channel channel, EventHandler eventHandler) {
		super(channel);
		handler = eventHandler;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope,
							   AMQP.BasicProperties properties, byte[] body) throws IOException {
		String jsonMessage = new String(body, "UTF-8");
		ParsableEvent parsableEvent = new JsonParsableEvent(jsonMessage);
		try {
			handler.handle(parsableEvent);
		} finally {
			getChannel().basicAck(envelope.getDeliveryTag(), false);
		}
	}
}
