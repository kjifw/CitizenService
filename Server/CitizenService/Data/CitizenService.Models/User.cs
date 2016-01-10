namespace CitizenService.Models
{
    using System.Threading.Tasks;
    using System.Security.Claims;
    using System.Collections.Generic;

    using Microsoft.AspNet.Identity;
    using Microsoft.AspNet.Identity.EntityFramework;
    
    public class User : IdentityUser
    {
        private ICollection<Issue> upVotedIssues;
        private ICollection<Issue> reportedAsIncorrectIssues;
        private ICollection<Issue> issues;
        private ICollection<Comment> comments;

        public User()
        {
            this.upVotedIssues = new HashSet<Issue>();
            this.reportedAsIncorrectIssues = new HashSet<Issue>();
            this.issues = new HashSet<Issue>();
            this.comments = new HashSet<Comment>();
        }

        public virtual ICollection<Issue> UpVotedIssues
        {
            get { return this.upVotedIssues; }
            set { this.upVotedIssues = value; }
        }

        public virtual ICollection<Issue> ReportedAsIncorrectIssues
        {
            get { return this.reportedAsIncorrectIssues; }
            set { this.reportedAsIncorrectIssues = value; }
        }

        public virtual ICollection<Issue> Issues
        {
            get { return this.issues; }
            set { this.issues = value; }
        }

        public virtual ICollection<Comment> Comments
        {
            get { return this.comments; }
            set { this.comments = value; }
        }

        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<User> manager, string authenticationType)
        {
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);

            return userIdentity;
        }
    }
}
