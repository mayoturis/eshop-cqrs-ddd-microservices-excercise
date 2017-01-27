using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Com.Marekturis.Common.Domain;
using Com.Marekturis.Common.infrastructure;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using EventHandler = Com.Marekturis.Common.Domain.EventHandler;

namespace rabbitskuska
{
    class Program
    {
        static void Main(string[] args)
        {
            using (EventPublisher eventPublisher = new RabbitMQEventPublisher(new DefaultEventJsonSerializer()))
            {
                EventHandler handler = new SomeEventHandler();
                eventPublisher.RegisterHandler(handler);
                eventPublisher.Publish(new SomeEvent());
                Console.WriteLine("koniec");
                Console.ReadLine();
                /*var factory = new ConnectionFactory {HostName = "localhost"};
                var connection = factory.CreateConnection();
                var channel = connection.CreateModel();
                channel.ExchangeDeclare("eshop_events", "direct");
                var queueName = channel.QueueDeclare().QueueName;

                channel.QueueBind(
                    queue: queueName,
                    exchange: "eshop_events",
                    routingKey: "ProductCreated");


                IBasicConsumer consumer = new RabbitMQConsumer(eventHandler, channel);
                channel.BasicConsume(queue: queueName, consumer: consumer);*/
            }
        }

        private class SomeEvent : Event
        {
            public DateTime OccuredOn { get; } = DateTime.Now;
            public string Name { get; } = "meno";
            private int nieco = 5;
            public string Grg { get; set; } = "d";
        }

        private class SomeEventHandler : EventHandler
        {
            public string EventToHandle { get; } = "ProductCreated";
            public void Handle(ParsableEvent e)
            {
                Console.WriteLine(e.Name);
                Console.WriteLine(e.OccuredOn);
                Console.WriteLine(e.GetInt("nieco"));
            }
        }
    }
}
