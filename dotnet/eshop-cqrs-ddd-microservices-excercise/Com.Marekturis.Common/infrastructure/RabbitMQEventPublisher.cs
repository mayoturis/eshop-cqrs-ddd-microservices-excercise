using System;
using Com.Marekturis.Common.Application;
using Com.Marekturis.Common.Domain;
using RabbitMQ.Client;
using EventHandler = Com.Marekturis.Common.Domain.EventHandler;

namespace Com.Marekturis.Common.infrastructure
{
    public class RabbitMQEventPublisher : EventPublisher
    {
        private const string EXCHANGE_NAME = "eshop_events";

        private readonly IConnection connection;
        private readonly IModel channel;

        private EventJsonSerializer serializer;

        public RabbitMQEventPublisher(EventJsonSerializer serializer)
        {
            this.serializer = serializer;

            var factory = new ConnectionFactory {HostName = "localhost"};
            connection = factory.CreateConnection();
            channel = connection.CreateModel();
            channel.ExchangeDeclare(EXCHANGE_NAME, "direct");
        }

        public void RegisterHandler(EventHandler eventHandler)
        {
            var queueName = channel.QueueDeclare().QueueName;

            channel.QueueBind(
                queue: queueName,
                exchange: EXCHANGE_NAME,
                routingKey: eventHandler.EventToHandle);


            IBasicConsumer consumer = new RabbitMQConsumer(eventHandler, channel);
            channel.BasicConsume(queue: queueName, consumer: consumer);
        }

        public void Publish(Event e)
        {
            var jsonEvent = serializer.FromEvent(e);
            channel.BasicPublish(EXCHANGE_NAME, e.Name, null, System.Text.Encoding.UTF8.GetBytes(jsonEvent));
        }

        public void Dispose()
        {
            if (channel != null && channel.IsOpen)
            {
                channel.Close();
            }

            if (connection != null && connection.IsOpen)
            {
                connection.Close();
            }
        }
    }
}