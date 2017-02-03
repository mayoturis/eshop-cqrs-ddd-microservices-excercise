using Com.Marekturis.Common.Domain.Event;

namespace Com.Marekturis.Common.Application.Serialization
{
    public interface EventJsonSerializer
    {
        string FromEvent(Event e);
    }
}