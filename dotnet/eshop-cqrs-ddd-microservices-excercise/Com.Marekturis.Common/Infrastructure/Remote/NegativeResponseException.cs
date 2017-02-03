using System;
using System.Net;
using System.Runtime.Serialization;

namespace Com.Marekturis.Common.Infrastructure.Remote
{
    public class NegativeResponseException : Exception
    {
        public HttpStatusCode StatusCode { get; }

        public NegativeResponseException(HttpStatusCode statusCode)
        {
            StatusCode = statusCode;
        }

        public NegativeResponseException()
        {
        }

        public NegativeResponseException(string message) : base(message)
        {
        }

        public NegativeResponseException(string message, Exception innerException) : base(message, innerException)
        {
        }

        protected NegativeResponseException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}