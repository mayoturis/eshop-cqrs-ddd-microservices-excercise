using System;

namespace Com.Marekturis.Product2.Model.Domain.Category
{
    public class Category
    {
        public int Id { get;  set; }

        private string name;
        public string Name
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

        public Category(string name)
        {
            Name = name;
        }

        protected Category()
        {

        }
    }
}