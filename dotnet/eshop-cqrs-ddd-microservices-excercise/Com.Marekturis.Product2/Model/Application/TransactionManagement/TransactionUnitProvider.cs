using System;

namespace Com.Marekturis.Product2.Model.Application.TransactionManagement
{
    public interface TransactionUnitProvider : IDisposable
    {
        TransactionUnitProvider Init();
        void Commit();
    }
}