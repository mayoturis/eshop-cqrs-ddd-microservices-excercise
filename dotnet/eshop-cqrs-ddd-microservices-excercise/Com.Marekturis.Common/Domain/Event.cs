using System;

namespace Com.Marekturis.Common.Domain
{
    public interface Event
    {
        DateTime OccuredOn { get; }
        string Name { get; }
    }
}