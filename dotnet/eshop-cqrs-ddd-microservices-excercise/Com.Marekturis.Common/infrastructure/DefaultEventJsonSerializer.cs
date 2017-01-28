using System;
using Com.Marekturis.Common.Application;
using Com.Marekturis.Common.Domain;
using Newtonsoft.Json;

namespace Com.Marekturis.Common.infrastructure
{
    public class DefaultEventJsonSerializer : EventJsonSerializer
    {
        public string FromEvent(Event e)
        {
            JsonSerializerSettings settings = new JsonSerializerSettings();
            settings.Converters.Add(new DateTimeConverter());
            return JsonConvert.SerializeObject(e, settings);
        }


        private class DateTimeConverter : JsonConverter
        {
            public override bool CanConvert(Type objectType)
            {
                return objectType == typeof(DateTime);
            }

            public override void WriteJson(JsonWriter writer, object value, Newtonsoft.Json.JsonSerializer serializer)
            {
                // The CanConvert method guarantees the value will be a DateTime
                var date = (DateTime) value;
                var milisTimestamp = (long) date.Subtract(new DateTime(1970, 1, 1)).TotalMilliseconds;
                writer.WriteValue(milisTimestamp);
            }

            public override bool CanRead => false;

            public override object ReadJson(JsonReader reader, Type objectType, object existingValue, Newtonsoft.Json.JsonSerializer serializer)
            {
                throw new NotImplementedException();
            }
        }
    }
}