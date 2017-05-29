using System;

namespace Com.Marekturis.Common.Domain.Event
{
    public class EventBase : Event
    {
        public DateTime OccuredOn { get; private set; }
        public string Name { get; private set; }

        public EventBase()
        {
            OccuredOn = DateTime.Now;
            Name = GetType().Name;
        }
    }
}