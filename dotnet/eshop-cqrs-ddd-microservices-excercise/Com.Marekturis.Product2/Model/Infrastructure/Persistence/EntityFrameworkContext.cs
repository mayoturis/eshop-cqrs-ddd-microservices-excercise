using System.Data.Entity;
using Com.Marekturis.Product2.Model.Domain.Category;

namespace Com.Marekturis.Product2.Model.Infrastructure.Persistence
{
    public class EntityFrameworkContext : DbContext
    {
        public DbSet<Domain.Product.Product> Products { get; set; }
        public DbSet<Category> Categories { get; set; }

        public EntityFrameworkContext()
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
                .HasKey(category => category.Id);

            modelBuilder.Entity<Category>()
                .Property(category => category.Name)
                .IsRequired();
        }

        private void OnProductCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Domain.Product.Product>()
                .HasKey(product => product.Id);

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