namespace CitizenService.Services.Data.Contracts
{
    public interface ICommentsService
    {
        bool AddComment(int issueId, string userId, string text);

        bool DeleteComment(int issueId, string userId);
    }
}
