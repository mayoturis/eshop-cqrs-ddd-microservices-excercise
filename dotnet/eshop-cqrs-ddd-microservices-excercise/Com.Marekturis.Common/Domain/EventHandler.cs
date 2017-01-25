namespace Com.Marekturis.Common.Domain
{
    public interface EventHandler
    {
        string EventToHandle();
        void Handle(ParsableEvent e);
    }
}