using Castle.DynamicProxy;
using Com.Marekturis.Product.Infrastructure.Persistence;

namespace Com.Marekturis.Product.Application.TransactionManagement
{
    internal class TransactionInterceptor : IInterceptor
    {
        /*private readonly EntityFrameworkContext context;

        internal TransactionInterceptor(EntityFrameworkContext context)
        {
            this.context = context;
        }*/

        private readonly TransactionUnitProvider transactionUnitProvider;

        internal TransactionInterceptor(TransactionUnitProvider transactionUnitProvider)
        {
            this.transactionUnitProvider = transactionUnitProvider;
        }


        public void Intercept(IInvocation invocation)
        {
            if (!invocation.MethodInvocationTarget.IsDefined(typeof(TransactionalAttribute), true))
            {
                // if no transactional attribute is on method
                return;
            }

            using (var unit = transactionUnitProvider.Init())
            {
                invocation.Proceed();
                unit.Commit();
            }
        }
    }
}