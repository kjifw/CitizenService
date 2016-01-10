namespace CitizenService.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;

    public class Issue
    {
        private ICollection<ImageData> imagesData;
        private ICollection<User> upVotedUsers;
        private ICollection<User> reportedAsIncorrectUsers;
        private ICollection<Comment> comments;

        public Issue()
        {
            this.imagesData = new HashSet<ImageData>();
            this.upVotedUsers = new HashSet<User>();
            this.reportedAsIncorrectUsers = new HashSet<User>();
            this.comments = new HashSet<Comment>();
        }

        [Key]
        public int Id { get; set; }

        public bool IsDeleted { get; set; }

        [Required]
        public string Title { get; set; }

        [Required]
        public string Description { get; set; }

        [Required]
        public string City { get; set; }

        [Required]
        public DateTime PublishDate { get; set; }

        public ImageData FrontImageData { get; set; }

        public virtual ICollection<ImageData> ImagesData
        {
            get { return this.imagesData; }
            set { this.imagesData = value; }
        }

        public virtual ICollection<User> UpVotedUsers
        {
            get { return this.upVotedUsers; }
            set { this.upVotedUsers = value; }
        }

        public virtual ICollection<User> ReportedAsIncorrectUsers
        {
            get { return this.reportedAsIncorrectUsers; }
            set { this.reportedAsIncorrectUsers = value; }
        }

        public virtual ICollection<Comment> Comments
        {
            get { return this.comments; }
            set { this.comments = value; }
        }

        public bool IsAnonymous { get; set; }

        public string UserId { get; set; }

        public virtual User User { get; set; }
    }
}
