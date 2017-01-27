using Com.Marekturis.Product2.Model.Application.TransactionManagement;

namespace Com.Marekturis.Product2.Model.Infrastructure.Persistence
{
    public class EntityFrameworkContextTransactionUnitProvider : TransactionUnitProvider
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