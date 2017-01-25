using System;

namespace Com.Marekturis.Common.Domain
{
    public class JsonParsableEvent : ParsableEvent
    {
        public DateTime OccuredOn => GetDateTime("CccuredOn");

        public string Name => GetString("Name");

        public string GetString(string key)
        {
            throw new NotImplementedException();
        }

        public int GetInt(string key)
        {
            throw new NotImplementedException();
        }

        public long GetLong(string key)
        {
            throw new NotImplementedException();
        }

        public DateTime GetDateTime(string key)
        {
            throw new NotImplementedException();
        }
    }
}