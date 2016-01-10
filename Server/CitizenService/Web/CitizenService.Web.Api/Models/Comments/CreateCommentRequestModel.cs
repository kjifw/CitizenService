namespace CitizenService.Web.Api.Models.Comments
{
    using System.ComponentModel.DataAnnotations;

    public class CreateCommentRequestModel
    {
        [Required]
        public int IssueId { get; set; }

        [Required]
        public int Text { get; set; }
    }
}