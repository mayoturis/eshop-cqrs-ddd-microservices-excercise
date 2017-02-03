using System;

namespace Com.Marekturis.Common.Domain.Event
{
    public interface Event
    {
        DateTime OccuredOn { get; }
        string Name { get; }
    }
}