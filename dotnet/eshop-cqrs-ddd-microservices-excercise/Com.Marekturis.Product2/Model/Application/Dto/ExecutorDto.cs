﻿using Com.Marekturis.Common.Application.Authorization;

namespace Com.Marekturis.Product2.Model.Application.Dto
{
    public class ExecutorDto : Authorizable
    {
        public string ExecutorToken { get; set; }
    }
}