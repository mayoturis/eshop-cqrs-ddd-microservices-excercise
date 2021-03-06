﻿ using System;
using Com.Marekturis.Common.Application.TransactionManagement;

namespace Com.Marekturis.Product2.Model.Infrastructure.Persistence
{
    public class EntityFrameworkContextTransactionUnitProvider : TransactionUnitProvider
    {
        [ThreadStatic]
        private static EntityFrameworkContext context;

        public void Dispose()
        {
            context.Dispose();
        }

        public TransactionUnitProvider Init()
        {
            context = new EntityFrameworkContext();
            return this;
        }

        public void Commit()
        {
            context.SaveChanges();
        }

        public EntityFrameworkContext GetUnit()
        {
            return context;
        }
    }
}