namespace Com.Marekturis.Common.Domain.Event
{
    public interface EventHandler
    {
        string EventToHandle { get; }
        void Handle(ParsableEvent e);
    }
}