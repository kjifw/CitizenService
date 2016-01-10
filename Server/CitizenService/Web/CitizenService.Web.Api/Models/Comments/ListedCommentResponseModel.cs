namespace CitizenService.Web.Api.Models.Comments
{
    using System;

    using AutoMapper;

    using Infrastructure.Mappings;
    using CitizenService.Models;

    public class ListedCommentResponseModel: IMapFrom<Comment>, IHaveCustomMappings
    {
        public DateTime CommentDate { get; set; }

        public string Author { get; set; }

        public string Text { get; set; }

        public void CreateMappings(IConfiguration configuration)
        {
            configuration.CreateMap<Comment, ListedCommentResponseModel>()
                .ForMember(la => la.Author, opts => opts.MapFrom(cm => cm.User.UserName));
        }
    }
}