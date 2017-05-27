using System.Collections.Generic;
using System.Linq;
using Com.Marekturis.Common.Domain;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction
{
    public class MongoDBCollectionRetriever
    {
        protected static IMongoClient _client;
        protected static IMongoDatabase _database;

        private TrackingMongoCollectionCreator _trackingCollectionCreator = new TrackingMongoCollectionCreator();

        private Dictionary<string, object> collections = new Dictionary<string, object>();

        public MongoDBCollectionRetriever()
        {
            _client = new MongoClient();
            _database = _client.GetDatabase("test");
        }

        public BasicMongoCollection<TAggregateRoot> GetCollection<TAggregateRoot>(string collectionName)
            where TAggregateRoot : AggregateRoot
        {
            if (!collections.ContainsKey(collectionName))
            {
                collections.Add(collectionName,
                    _trackingCollectionCreator.CreateTrackingCollection(_database.GetCollection<TAggregateRoot>(collectionName)));
            }

            return collections[collectionName] as BasicMongoCollection<TAggregateRoot>;
        }

        public List<object> GetTrackedCollections()
        {
            return collections.Values.ToList();
        }
    }
}