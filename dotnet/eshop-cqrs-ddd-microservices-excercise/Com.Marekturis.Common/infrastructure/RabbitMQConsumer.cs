using System;
using Com.Marekturis.Common.Domain;
using RabbitMQ.Client;
using EventHandler = Com.Marekturis.Common.Domain.EventHandler;

namespace Com.Marekturis.Common.infrastructure
{
    public class RabbitMQConsumer : DefaultBasicConsumer
    {
        private readonly IModel channel;
        private readonly EventHandler eventHandler;

        public RabbitMQConsumer(EventHandler eventHandler, IModel channel)
        {
            this.eventHandler = eventHandler;
            this.channel = channel;
        }

        public override void HandleBasicDeliver(string consumerTag, ulong deliveryTag, bool redelivered, string exchange, string routingKey,
            IBasicProperties properties, byte[] body)
        {
            var jsonMessage = System.Text.Encoding.UTF8.GetString(body, 0, body.Length);
            ParsableEvent parsableEvent = new JsonParsableEvent(jsonMessage);
            try {
                eventHandler.Handle(parsableEvent);
            } finally {
                channel.BasicAck(deliveryTag, false);
            }
        }
    }
}