using System.Collections.Generic;
using System.Linq;
using Com.Marekturis.Common.Domain;
using Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction.Proxies;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction
{
    public class TrackingMongoCollection<TAggregateRoot, TAggregateRootProxy> : BasicMongoCollection<TAggregateRoot>
        where TAggregateRoot : AggregateRoot
        where TAggregateRootProxy : TAggregateRoot, Proxy<TAggregateRoot>, new()
    {
        private IMongoCollection<TAggregateRoot> _mongoCollection;

        private Dictionary<string, TAggregateRoot> _idaggregateRootMap = new Dictionary<string, TAggregateRoot>();

        public TrackingMongoCollection(IMongoCollection<TAggregateRoot> mongoCollection)
        {
            _mongoCollection = mongoCollection;
        }

        public void InsertOne(TAggregateRoot aggregateRoot)
        {
            _mongoCollection.InsertOne(aggregateRoot);
            var proxy = GetProxiedAggregateRoot(aggregateRoot);
            Track(proxy);
        }

        public List<TAggregateRoot> Find(FilterDefinition<TAggregateRoot> filter)
        {
            var retrievedAggregateRoots = _mongoCollection.Find(filter).ToList();
            var aggregateRootsToReturn = new List<TAggregateRoot>();
            foreach (var retrievedAggregateRoot in retrievedAggregateRoots)
            {
                var aggregateRootToReturn = GetCorrectAggregateRoot(retrievedAggregateRoot);
                var proxiedAggregateRoot = GetProxiedAggregateRoot(aggregateRootToReturn);
                Track(proxiedAggregateRoot);
                aggregateRootsToReturn.Add(proxiedAggregateRoot);
            }

            return aggregateRootsToReturn;
        }

        private TAggregateRoot GetCorrectAggregateRoot(TAggregateRoot retrievedAggregateRoot)
        {
            if (_idaggregateRootMap.ContainsKey(retrievedAggregateRoot.Id))
            {
                return _idaggregateRootMap[retrievedAggregateRoot.Id];
            }
            return retrievedAggregateRoot;
        }

        private TAggregateRootProxy GetProxiedAggregateRoot(TAggregateRoot aggregateRoot)
        {
            var proxyFactory = new TAggregateRootProxy();
            return (TAggregateRootProxy) proxyFactory.Initialize(aggregateRoot);
        }

        private void Track(TAggregateRoot aggregateRoot)
        {
            _idaggregateRootMap[aggregateRoot.Id] = aggregateRoot;
        }

        public List<TAggregateRoot> GetTrackedAggregateRoots()
        {
            return _idaggregateRootMap.Values.ToList();
        }
    }
}