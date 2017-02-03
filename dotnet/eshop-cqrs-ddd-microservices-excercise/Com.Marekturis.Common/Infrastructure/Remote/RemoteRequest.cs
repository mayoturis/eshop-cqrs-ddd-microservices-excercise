using System;
using System.Collections.Generic;
using System.ComponentModel;
using Com.Marekturis.Common.Application.Authorization;
using RestSharp;

namespace Com.Marekturis.Common.Infrastructure.Remote
{
    public class RemoteRequest
    {
        public RestRequest RestSharpRequest { get; }

        public RemoteRequest(string path, Method method)
        {
            RestSharpRequest = new RestRequest(path, method);
            RestSharpRequest.RequestFormat = DataFormat.Json;
        }

        public void AddRawBody(object body)
        {
            RestSharpRequest.AddBody(body);
        }

        public void AddDtoBody(object dto)
        {
            RestSharpRequest.AddBody(DtoToCamelCase(dto));
            if (dto is Authorizable)
            {
                 Authorize((Authorizable) dto);
            }
        }


        private object DtoToCamelCase(object dto)
        {
            return ToCamelCaseDictionary(dto);
        }

        public IDictionary<string, object> ToCamelCaseDictionary(object dto)
        {
            if (dto == null)
            {
                return null;
            }

            var dictionary = new Dictionary<string, object>();
            foreach (PropertyDescriptor property in TypeDescriptor.GetProperties(dto))
            {
                AddPropertyToDictionary(property, dto, dictionary);
            }

            return dictionary;
        }

        private void AddPropertyToDictionary(PropertyDescriptor property, object source, Dictionary<string, object> dictionary)
        {
            var value = property.GetValue(source);
            dictionary.Add(PascalCaseToCamelCase(property.Name), value);
        }

        private string PascalCaseToCamelCase(string str)
        {
            return Char.ToLowerInvariant(str[0]) + str.Substring(1);
        }

        public void Authorize(Authorizable authorizable)
        {
            RestSharpRequest.AddHeader("Authorization", "TOKEN " + authorizable.ExecutorToken);
        }
    }
}