using Castle.DynamicProxy;

namespace Com.Marekturis.Common.Application.TransactionManagement
{
    public class TransactionInterceptor : IInterceptor
    {
        private readonly TransactionUnitProvider transactionUnitProvider;

        public TransactionInterceptor(TransactionUnitProvider transactionUnitProvider)
        {
            this.transactionUnitProvider = transactionUnitProvider;
        }


        public void Intercept(IInvocation invocation)
        {
            if (!HasTransactionalAttribute(invocation))
            {
                invocation.Proceed();
                return;
            }

            using (var unit = transactionUnitProvider.Init())
            {
                invocation.Proceed();
                unit.Commit();
            }
        }

        private bool HasTransactionalAttribute(IInvocation invocation)
        {
            return invocation.MethodInvocationTarget.IsDefined(typeof(TransactionalAttribute), true)
                   || invocation.InvocationTarget.GetType().IsDefined(typeof(TransactionalAttribute), true);
        }
    }
}