using System.ComponentModel.DataAnnotations;
using Com.Marekturis.Product2.Model.Application.Authorization;

namespace Com.Marekturis.Product2.Model.Application.Dto
{
    public class ExecutorDto : Authorizable
    {
        [Required]
        public string ExecutorToken { get; set; }
    }
}