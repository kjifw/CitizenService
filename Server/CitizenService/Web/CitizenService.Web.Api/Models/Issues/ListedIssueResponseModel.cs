namespace CitizenService.Web.Api.Models.Issues
{
    using System;
    using System.Collections.Generic;

    using AutoMapper;

    using CitizenService.Models;
    using Infrastructure.Mappings;
    
    public class ListedIssueResponseModel : IMapFrom<Issue>, IHaveCustomMappings
    {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public int UpVotesCount { get; set; }

        public string Author { get; set; }

        public DateTime PublishDate { get; set; }

        public string ImageUrl { get; set; }

        public ICollection<string> ImagesUrls { get; set; }

        public void CreateMappings(IConfiguration configuration)
        {
            configuration.CreateMap<Issue, ListedIssueResponseModel>()
                .ForMember(la => la.ImageUrl, opts => opts.MapFrom(a => a.FrontImageData != null ? a.FrontImageData.Url : null))
                .ForMember(la => la.Author, opts => opts.MapFrom(a => a.User.UserName))
                .ForMember(la => la.ImagesUrls, opts => opts.MapFrom(a => a.ImagesData));
        }
    }
}