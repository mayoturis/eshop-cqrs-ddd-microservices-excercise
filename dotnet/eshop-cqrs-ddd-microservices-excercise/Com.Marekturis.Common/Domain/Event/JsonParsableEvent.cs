using System;

namespace Com.Marekturis.Common.Domain.Event
{
    public class JsonParsableEvent : JsonParsable, ParsableEvent
    {

        public JsonParsableEvent(string json) : base(json)
        {
        }

        public DateTime OccuredOn => GetDateTime("OccuredOn");

        public string Name => GetString("Name");
    }
}