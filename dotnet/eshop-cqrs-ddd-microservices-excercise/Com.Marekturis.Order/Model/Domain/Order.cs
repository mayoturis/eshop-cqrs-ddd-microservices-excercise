using System;
using System.Collections.Generic;
using Com.Marekturis.Common.Domain;
using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson.Serialization.IdGenerators;
using MongoDB.Bson.Serialization.Options;

namespace Com.Marekturis.Order.Model.Domain
{
    public class Order : AggregateRoot
    {
        [BsonId(IdGenerator = typeof(StringObjectIdGenerator))]
        public string Id { get; protected set; }

        [BsonElement("created")]
        public DateTime Created { get; private set; }

        [BsonElement("expedited")]
        public bool Expedited { get; private set; }

        [BsonElement("user_id")]
        public int UserId { get; private set; }

        [BsonElement("ordered_products")]
        public List<OrderedProduct> OrderedProducts { get; private set; }

        public Order(DateTime created, int userId, List<OrderedProduct> orderedProducts)
        {
            Created = created;
            UserId = userId;
            OrderedProducts = orderedProducts;
        }

        public Order(DateTime created, int userId) : this(created, userId, new List<OrderedProduct>())
        {
        }

        public virtual void Expedite()
        {
            Expedited = true;
        }
    }
}