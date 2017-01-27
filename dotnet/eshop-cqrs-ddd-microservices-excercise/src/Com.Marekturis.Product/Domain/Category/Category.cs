using System;

namespace Com.Marekturis.Product.Domain.Category
{
    internal class Category
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
                    throw new ArgumentNullException("Category name cannot be null");
                }

                name = value;
            }
        }

        internal int? ParentCategoryId { get; private set; }

        internal Category(string name, int? parentCategoryId = null)
        {
            Name = name;
            ParentCategoryId = parentCategoryId;
        }
    }
}