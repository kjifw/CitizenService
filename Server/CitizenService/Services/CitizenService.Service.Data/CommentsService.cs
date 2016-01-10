namespace CitizenService.Services.Data
{
    using System;

    using CitizenService.Data.Repositories;
    using Services.Data.Contracts;
    using Models;
    using System.Linq;
    public class CommentsService : ICommentsService
    {
        private IGenericRepository<Comment> commentsRepository;

        public CommentsService(IGenericRepository<Comment> commentsRepository)
        {
            this.commentsRepository = commentsRepository;
        }

        public bool AddComment(int issueId, string userId, string text)
        {
            var comment = new Comment()
            {
                IssueId = issueId,
                UserId = userId,
                Text = text
            };

            try
            {
                this.commentsRepository
                    .Add(comment);
            }
            catch
            {
                return false;
            }

            return true;
        }

        public bool DeleteComment(int id, string userId)
        {
            var comment = this.commentsRepository
                .All()
                .Where(cm => cm.Id == id && cm.UserId == userId)
                .FirstOrDefault();

            if(comment == null)
            {
                return false;
            }

            comment.IsDeleted = true;

            this.commentsRepository.SaveChanges();

            return true;
        }
    }
}
