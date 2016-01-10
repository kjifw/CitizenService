namespace CitizenService.Web.Api.Controllers
{
    using System.Web.Http;

    using Services.Data.Contracts;

    public class CommentsController : ApiController
    {
        private ICommentsService commentsService;

        public CommentsController(ICommentsService commentsService)
        {
            this.commentsService = commentsService;
        }
    }
}
