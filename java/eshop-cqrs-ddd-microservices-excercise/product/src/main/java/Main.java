import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Marek Turis
 */
public class Main {

	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] argv)
			throws java.io.IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		String eventName = "ProductCreated";
		String message = "product_data";

		channel.basicPublish(EXCHANGE_NAME, eventName, null, message.getBytes());
		System.out.println(" [x] Sent '" + eventName + "':'" + message + "'");

		channel.close();
		connection.close();
	}
}
