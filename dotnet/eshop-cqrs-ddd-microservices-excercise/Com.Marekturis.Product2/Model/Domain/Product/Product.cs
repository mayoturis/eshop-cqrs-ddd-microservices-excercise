using System;

namespace Com.Marekturis.Product2.Model.Domain.Product
{
    public class Product
    {
        public int ID { get; set; }
        private string name;

        public string Name
        {
            get { return name; }
            private set
            {
                if (value == null)
                {
                    throw new ArgumentNullException("Name cannot be null");
                }
                name = value;
            }
        }

        public int CategoryId { get; private set; }

        public decimal Price { get; private set; }

        public Product(string name, decimal price, int categoryId)
        {
            Name = name;
            Price = price;
            CategoryId = categoryId;
        }

        protected Product()
        {

        }
    }
}