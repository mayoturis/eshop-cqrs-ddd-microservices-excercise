using System;

namespace Com.Marekturis.Product.Application.TransactionManagement
{
    internal interface TransactionUnitProvider : IDisposable
    {
        TransactionUnitProvider Init();
        void Commit();
    }
}