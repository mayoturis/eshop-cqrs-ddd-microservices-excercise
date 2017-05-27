using System.Collections.Generic;
using Com.Marekturis.Common.Domain;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Commiters
{
    public interface MongoDbCommiter<TAggregateRoot> where TAggregateRoot : AggregateRoot
    {
        void CommitChanged(List<TAggregateRoot> orders);
    }
}