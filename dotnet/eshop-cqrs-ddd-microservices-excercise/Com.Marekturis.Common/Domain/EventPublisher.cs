using System;

namespace Com.Marekturis.Common.Domain
{
    public interface EventPublisher : IDisposable
    {
        void RegisterHandler(EventHandler eventHandler);
        void Publish(Event e);
    }
}