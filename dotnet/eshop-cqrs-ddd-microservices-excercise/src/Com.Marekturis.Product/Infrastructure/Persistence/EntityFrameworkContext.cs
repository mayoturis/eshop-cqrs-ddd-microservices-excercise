using System.Data.Entity;
using Com.Marekturis.Product.Domain.Category;

namespace Com.Marekturis.Product.Infrastructure.Persistence
{
    internal class EntityFrameworkContext : DbContext
    {
        internal DbSet<Domain.Product.Product> Products { get; set; }
        internal DbSet<Category> Categories { get; set; }

        internal EntityFrameworkContext()
        {
            Database.SetInitializer(new DropCreateDatabaseIfModelChanges<EntityFrameworkContext>());
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            OnProductCreating(modelBuilder);
            OnCategoryCreating(modelBuilder);
        }

        private void OnCategoryCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Category>()
                .HasKey(category => category.ID);

            modelBuilder.Entity<Category>()
                .Property(category => category.Name)
                .IsRequired();
        }

        private void OnProductCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Domain.Product.Product>()
                .HasKey(product => product.ID);

            modelBuilder.Entity<Domain.Product.Product>()
                .Property(product => product.CategoryId)
                .IsRequired();

            modelBuilder.Entity<Domain.Product.Product>()
                .Property(product => product.Price)
                .IsRequired();

            modelBuilder.Entity<Domain.Product.Product>()
                .Property(product => product.Name)
                .IsRequired();
        }
    }
}