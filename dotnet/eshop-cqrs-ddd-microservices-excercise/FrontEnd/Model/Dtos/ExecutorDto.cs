using Com.Marekturis.Common.Application.Authorization;

namespace FrontEnd.Model.Dtos
{
    public class ExecutorDto : Authorizable
    {
        public string ExecutorToken { get; set; }
    }
}