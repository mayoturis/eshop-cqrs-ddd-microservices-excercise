using System;

namespace Com.Marekturis.Common.Domain.Event
{
    public interface EventPublisher : IDisposable
    {
        void RegisterHandler(EventHandler eventHandler);
        void Publish(Domain.Event.Event e);
    }
}