using System;

namespace Com.Marekturis.Product.Domain.Product
{
    internal class Product
    {
        internal int ID { get; protected set; }
        private string name;

        internal string Name
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

        internal int CategoryId { get; private set; }

        internal decimal Price { get; private set; }

        internal Product(string name, decimal price, int categoryId)
        {
            Name = name;
            Price = price;
            CategoryId = categoryId;
        }
    }
}