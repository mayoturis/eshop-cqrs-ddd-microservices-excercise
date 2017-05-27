using System;
using System.Collections.Generic;
using System.Linq;
using Com.Marekturis.Common.Application.TransactionManagement;
using Com.Marekturis.Order.Model.Domain;
using MongoDB.Driver;

namespace Com.Marekturis.Order.Model.Infrastructure
{
    public class Skuska
    {

        protected static IMongoClient _client;
        protected static IMongoDatabase _database;

        private TransactionUnitProvider _unitProvider;
        private OrderRepository _orderRepository;

        public Skuska(TransactionUnitProvider unitProvider, OrderRepository orderRepository)
        {
            _unitProvider = unitProvider;
            _orderRepository = orderRepository;
        }

        public void ido()
        {
            _client = new MongoClient();
            _database = _client.GetDatabase("test");

            var document = new Domain.Order(DateTime.Now, 5, new List<OrderedProduct>() {new OrderedProduct(2,5)});
            var collection = _database.GetCollection<Domain.Order>("orders");
            collection.InsertOne(document);

            Console.WriteLine(document.Id);

            var filter = Builders<Domain.Order>.Filter.Eq("Id", document.Id);
            var result = collection.Find(filter).ToList();
            Console.WriteLine(result.First().UserId);
        }

        public void fdo()
        {
            _unitProvider.Init();
            var order = new Domain.Order(DateTime.Now, 11, new List<OrderedProduct>() {new OrderedProduct(4,3)});
            _orderRepository.AddOrder(order);
            _unitProvider.Commit();

            _unitProvider.Init();

            var retrievedOrder = _orderRepository.GetOrderById(order.Id);
            retrievedOrder.Expedite();

            _unitProvider.Commit();

            _unitProvider.Init();

            var againRetrievedOrder = _orderRepository.GetOrderById(order.Id);

            _unitProvider.Commit();

        }
    }
}