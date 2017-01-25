using Com.Marekturis.Common.Domain;

namespace Com.Marekturis.Common.Application
{
    public interface EventJsonSerializer
    {
        string FromEvent(Event e);
    }
}