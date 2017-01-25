using System;

namespace Com.Marekturis.Common.Domain
{
    public interface ParsableEvent : Event
    {
        string GetString(string key);
        int GetInt(string key);
        long GetLong(string key);
        DateTime GetDateTime(string key);
    }
}