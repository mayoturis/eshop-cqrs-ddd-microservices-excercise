using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace Com.Marekturis.Common.Domain
{
    public class JsonParsable : Parsable
    {
        private readonly Dictionary<string, dynamic> fields = new Dictionary<string, dynamic>();

        public JsonParsable(string json)
        {
            var jsonFields = JsonConvert.DeserializeObject<Dictionary<string, dynamic>>(json);
            initializeLowerCaseFields(jsonFields);
        }

        private void initializeLowerCaseFields(Dictionary<string, dynamic> jsonFields)
        {
            foreach (var entry in jsonFields)
            {
                var normaliedKey = normalizeFieldKey(entry.Key);
                fields.Add(normaliedKey, entry.Value);
            }
        }

        private string normalizeFieldKey(string key)
        {
            return key.ToLower().Replace("_", "");
        }

        public string GetString(string key)
        {
            return GetValue<string>(key);
        }

        public int GetInt(string key)
        {
            return GetValue<int>(key);
        }

        public long GetLong(string key)
        {
            return GetValue<long>(key);
        }

        public DateTime GetDateTime(string key)
        {
            long timestampInMilis = GetLong(key);
            var dateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc);
            return dateTime.AddMilliseconds(timestampInMilis);
        }

        private TValueType GetValue<TValueType>(string key)
        {
            return (TValueType) fields[normalizeFieldKey(key)];
        }
    }
}