namespace Com.Marekturis.Common.Domain
{
    public interface EventPublisher
    {
        void RegisterHandler(EventHandler eventHandler);
        void Publish(Event e);
    }
}