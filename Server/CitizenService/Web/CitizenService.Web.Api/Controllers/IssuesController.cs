namespace CitizenService.Web.Api.Controllers
{
    using System;
    using System.Web.Http;
    using System.Linq;

    using Microsoft.AspNet.Identity;
    using AutoMapper.QueryableExtensions;

    using CitizenService.Services.Data.Contracts;
    using Infrastructure.Validation;
    using Models.Issues;
    
    [Authorize]
    public class IssuesController : ApiController
    {
        private IIssuesService issuesService;

        public IssuesController(IIssuesService issuesService)
        {
            this.issuesService = issuesService;
        }

        [ValidateModel]
        public IHttpActionResult Post(CreateIssueRequestModel createAdvertRequestModel)
        {
            var userId = this.User.Identity.GetUserId();

            var newIssue = this.issuesService.CreateIssue(userId, createAdvertRequestModel.City, createAdvertRequestModel.Title,
                createAdvertRequestModel.Description, createAdvertRequestModel.IsAnonymous, DateTime.UtcNow);

            var issueResult = this.issuesService
                .GetIssueDetails(newIssue.Id)
                .ProjectTo<ListedIssueResponseModel>()
                .FirstOrDefault();

            return this.Created(string.Format("/api/issues/{0}", issueResult.Id), issueResult);
        }

        [Route("api/issues/my")]
        public IHttpActionResult GetMyIssues()
        {
            var userId = this.User.Identity.GetUserId();

            var issuesResult = this.issuesService
                .GetIssuesOfUser(userId)
                .ProjectTo<ListedIssueResponseModel>()
                .ToList();

            return this.Ok(issuesResult);
        }

        [Route("api/issues/search/{city}/{title}")]
        public IHttpActionResult SearchIssues(string city, string title)
        {
            var issuesResult = this.issuesService
                .GetIssuesByFilters(city, title)
                .ProjectTo<ListedIssueResponseModel>()
                .ToList();

            return this.Ok(issuesResult);
        }

        [Route("api/issues/sortedbyvotes/{count}")]
        public IHttpActionResult GetIssuesSoredByVotes(int count)
        {
            var issuesResult = this.issuesService
                .GetTopVotedIssues(count)
                .ProjectTo<ListedIssueResponseModel>()
                .ToList();

            return this.Ok(issuesResult);
        }

        [Route("api/issues/voteup/{issueId}")]
        [HttpGet]
        public IHttpActionResult VoteUp(int issueId)
        {
            var userId = this.User.Identity.GetUserId();

            int votesCount = this.issuesService
                .UpVoteIssue(issueId, userId);

            var result = new IssueUpVotesResponseModel();
            result.VotesCount = votesCount;

            return this.Ok(result);
        }

        [Route("api/issues/report/{issueId}")]
        [HttpGet]
        public IHttpActionResult ReportAsIncorrect(int issueId)
        {
            var userId = this.User.Identity.GetUserId();

            this.issuesService
                .ReportAsIncorrectIssue(issueId, userId);

            return this.Ok();
        }
    }
}
