using Com.Marekturis.Product.Application.TransactionManagement;

namespace Com.Marekturis.Product.Infrastructure.Persistence
{
    internal class EntityFrameworkContextTransactionUnitProvider : TransactionUnitProvider
    {
        private EntityFrameworkContext context;

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