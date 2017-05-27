using Com.Marekturis.Common.Domain;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies
{
    public interface Proxy<TAggregateRoot> where TAggregateRoot : AggregateRoot
    {
        Proxy<TAggregateRoot> Initialize(TAggregateRoot aggregateRoot);
    }
}