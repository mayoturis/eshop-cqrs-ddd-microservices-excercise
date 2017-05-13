using System;
using System.Runtime.Serialization;

namespace Com.Marekturis.Common.Application.Validation
{
    public class ValidationException : Exception
    {
        public string ErrorMessage => Message;

        public ValidationException(string message) : base(message)
        {
        }

        public ValidationException(string message, Exception innerException) : base(message, innerException)
        {
        }
    }
}