namespace CitizenService.Services.Data.Contracts
{
    using System;
    using System.Linq;

    using Models;
    
    public interface IIssuesService
    {
        Issue CreateIssue(string userId, string city, string title, string description, bool isAnonymous, DateTime publishDate);

        IQueryable<Issue> GetIssueDetails(int id);

        IQueryable<Issue> GetIssuesOfUser(string userId);

        IQueryable<Issue> GetTopVotedIssues(int count);

        bool AddImageToIssue(int id, string userId, string imageUrl);

        bool DeleteIssue(int id, string userId);

        int UpVoteIssue(int id, string userId);

        bool ReportAsIncorrectIssue(int id, string userId);

        IQueryable<Issue> GetIssuesByFilters(string city, string title);
    }
}
