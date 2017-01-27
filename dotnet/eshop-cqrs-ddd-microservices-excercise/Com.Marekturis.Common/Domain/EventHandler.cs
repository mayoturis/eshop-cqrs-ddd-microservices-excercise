namespace Com.Marekturis.Common.Domain
{
    public interface EventHandler
    {
        string EventToHandle { get; }
        void Handle(ParsableEvent e);
    }
}