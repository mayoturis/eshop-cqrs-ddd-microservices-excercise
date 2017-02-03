using System;

namespace Com.Marekturis.Common.Application.TransactionManagement
{
    public interface TransactionUnitProvider : IDisposable
    {
        TransactionUnitProvider Init();
        void Commit();
    }
}