﻿namespace CitizenService.Web.Api.Controllers
{
    using System;
    using System.Net.Http;
    using System.Web.Hosting;
    using System.Web.Http;
    using System.Threading.Tasks;
    using Microsoft.AspNet.Identity;

    using Services.Data.Contracts;
    using Common.Constants;

    [Authorize]
    public class ImagesController : ApiController
    {
        private IImagesService imagesService;
        private IIssuesService issuesService;

        public ImagesController(IImagesService imagesService, IIssuesService issuesService)
        {
            this.imagesService = imagesService;
            this.issuesService = issuesService;
        }

        [Route("api/images/{issueId}")]
        public async Task<IHttpActionResult> Post(int issueId)
        {
            if (Request.Content.IsMimeMultipartContent() == false)
            {
                return this.BadRequest();
            }

            await Request.Content.LoadIntoBufferAsync();
            var provider = await Request.Content.ReadAsMultipartAsync(new MultipartMemoryStreamProvider());

            try
            {
                var fileName = await this.imagesService.StoreImage(provider.Contents[0], HostingEnvironment.MapPath(GlobalConstants.ImagesStoreLocation), issueId, "");
                var baseUrl = Request.RequestUri.GetLeftPart(UriPartial.Authority);
                var imageUrl = string.Format("{0}/images/{1}", baseUrl, fileName);

                var userId = this.User.Identity.GetUserId();

                this.issuesService.AddImageToIssue(issueId, userId, imageUrl);
            }
            catch
            {
                return this.BadRequest();
            }

            return this.Created(string.Format("/api/images/{0}", issueId), "");
        }
    }
}
