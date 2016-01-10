namespace CitizenService.Data
{
    using Microsoft.AspNet.Identity.EntityFramework;

    using CitizenService.Models;
    using System.Data.Entity;

    public class CitizenServiceDbContext : IdentityDbContext<User>, ICitizenServiceDbContext
    {
        public CitizenServiceDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {

        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<User>()
                .HasMany<Issue>(u => u.UpVotedIssues)
                .WithMany(a => a.UpVotedUsers)
                .Map(cs =>
                {
                    cs.MapLeftKey("UserId");
                    cs.MapRightKey("IssueId");
                    cs.ToTable("UpVotedUsersIssues");
                });

            modelBuilder.Entity<User>()
                .HasMany<Issue>(u => u.ReportedAsIncorrectIssues)
                .WithMany(a => a.ReportedAsIncorrectUsers)
                .Map(cs =>
                {
                    cs.MapLeftKey("UserId");
                    cs.MapRightKey("IssueId");
                    cs.ToTable("ReportedAsIncorrectUsersIssues");
                });
        }

        public static CitizenServiceDbContext Create()
        {
            return new CitizenServiceDbContext();
        }
    }
}
