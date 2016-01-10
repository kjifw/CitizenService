namespace CitizenService.Models
{
    public class Comment
    {
        public int Id { get; set; }

        public bool IsDeleted { get; set; }

        public int IssueId { get; set; }

        public virtual Issue Issue { get; set; }

        public string UserId { get; set; }

        public virtual User User { get; set; }

        public string Text { get; set; }
    }
}
