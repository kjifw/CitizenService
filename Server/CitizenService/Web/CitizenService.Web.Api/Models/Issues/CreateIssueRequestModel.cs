namespace CitizenService.Web.Api.Models.Issues
{
    using System.ComponentModel.DataAnnotations;

    public class CreateIssueRequestModel
    {
        [Required]
        public string City { get; set; }

        [Required]
        public string Title { get; set; }

        [Required]
        public string Description { get; set; }

        [Required]
        public bool IsAnonymous { get; set; }
    }
}