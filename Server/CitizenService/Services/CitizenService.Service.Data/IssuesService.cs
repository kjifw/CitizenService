namespace CitizenService.Services.Data
{
    using System;
    using System.Linq;

    using Models;
    using Services.Data.Contracts;
    using CitizenService.Data.Repositories;
    using Common.Constants;

    public class IssuesService : IIssuesService
    {
        private IGenericRepository<Issue> issues;
        private IGenericRepository<User> users;

        public IssuesService(IGenericRepository<Issue> issues, IGenericRepository<User> users)
        {
            this.issues = issues;
            this.users = users;
        }

        public bool AddImageToIssue(int id, string userId, string imageUrl)
        {
            var issue = this.issues.All()
                .Where(a => a.Id == id && a.UserId == userId)
                .FirstOrDefault();

            if (issue == null)
            {
                return false;
            }

            var imageData = new ImageData()
            {
                Url = imageUrl
            };

            if (issue.FrontImageData == null)
            {
                issue.FrontImageData = imageData;
            }

            issue.ImagesData.Add(imageData);
            this.issues.SaveChanges();

            return true;
        }

        public Issue CreateIssue(string userId, string city, string title, string description, bool isAnonymous, DateTime publishDate)
        {
            var issue = new Issue()
            {
                UserId = userId,
                City = city,
                Title = title,
                Description = description,
                IsAnonymous = isAnonymous,
                PublishDate = publishDate
            };

            this.issues.Add(issue);
            this.issues.SaveChanges();

            return issue;
        }

        public bool DeleteIssue(int id, string userId)
        {
            var issue = this.issues
                .All()
                .Where(i => i.Id == id && i.UserId == userId)
                .FirstOrDefault();

            if (issue == null)
            {
                return false;
            }

            issue.IsDeleted = true;
            this.issues.SaveChanges();

            return true;
        }

        public IQueryable<Issue> GetIssueDetails(int id)
        {
            return this.issues
                .All()
                .Where(i => i.Id == id);
        }

        public IQueryable<Issue> GetIssuesByFilters(string city)
        {
            return this.issues
                .All()
                .Where(i => i.City == city);
        }

        public IQueryable<Issue> GetIssuesOfUser(string userId)
        {
            var issues = this.issues
                .All()
                .Where(i => i.UserId == userId);

            return issues;
        }

        public bool ReportAsIncorrectIssue(int id, string userId)
        {
            var issue = this.issues
                .All()
                .Where(i => i.Id == id && i.UserId == userId)
                .FirstOrDefault();

            if (issue == null)
            {
                return false;
            }

            var user = this.users
                .All()
                .Where(u => u.Id == userId)
                .FirstOrDefault();

            issue.ReportedAsIncorrectUsers.Add(user);

            if(issue.ReportedAsIncorrectUsers.Count >= GlobalConstants.CountOfReportsToDeleteIssue)
            {
                issue.IsDeleted = true;
            }

            this.issues.SaveChanges();

            user.ReportedAsIncorrectIssues.Add(issue);
            this.users.SaveChanges();

            return true;
        }

        public bool UpVoteIssue(int id, string userId)
        {
            var issue = this.issues
                .All()
                .Where(i => i.Id == id && i.UserId == userId)
                .FirstOrDefault();

            if (issue == null)
            {
                return false;
            }

            var user = this.users
                .All()
                .Where(u => u.Id == userId)
                .FirstOrDefault();

            issue.UpVotedUsers.Add(user);
            this.issues.SaveChanges();

            user.UpVotedIssues.Add(issue);
            this.users.SaveChanges();

            return true;
        }
    }
}
