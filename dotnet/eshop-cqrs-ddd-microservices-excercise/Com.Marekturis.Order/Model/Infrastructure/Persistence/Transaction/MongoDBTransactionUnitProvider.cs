using System.Collections.Generic;
using Com.Marekturis.Common.Application.TransactionManagement;

namespace Com.Marekturis.Order.Model.Infrastructure.Persistence.Transaction
{
    public class MongoDBTransactionUnitProvider : TransactionUnitProvider
    {
        private MongoDBCollectionRetriever _collectionRetriever;

        private MongoCollectionsCommiter _collectionsCommiter;

        public MongoDBTransactionUnitProvider(MongoCollectionsCommiter collectionsCommiter)
        {
            _collectionsCommiter = collectionsCommiter;
        }

        public void Dispose()
        {

        }

        public TransactionUnitProvider Init()
        {
            _collectionRetriever = new MongoDBCollectionRetriever();
            return this;
        }

        public void Commit()
        {
            var trackedCollections = _collectionRetriever.GetTrackedCollections();
            _collectionsCommiter.CommitCollections(trackedCollections);
        }

        public MongoDBCollectionRetriever GetCollectionRetriever()
        {
            return _collectionRetriever;
        }
    }
}