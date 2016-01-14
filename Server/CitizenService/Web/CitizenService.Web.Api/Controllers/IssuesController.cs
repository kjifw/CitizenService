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
    }
}
