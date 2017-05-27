using System.Collections.Generic;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction
{
    public interface BasicMongoCollection<TAggregateRoot>
    {
        void InsertOne(TAggregateRoot entity);
        List<TAggregateRoot> Find(FilterDefinition<TAggregateRoot> filter);
    }
}