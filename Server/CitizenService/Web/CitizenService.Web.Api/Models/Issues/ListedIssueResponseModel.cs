namespace CitizenService.Web.Api.Models.Issues
{
    using System;

    using AutoMapper;

    using CitizenService.Models;
    using Infrastructure.Mappings;

    public class ListedIssueResponseModel : IMapFrom<Issue>, IHaveCustomMappings
    {
        public int Id { get; set; }

        public DateTime PublishDate { get; set; }

        public string ImageUrl { get; set; }

        public void CreateMappings(IConfiguration configuration)
        {
            configuration.CreateMap<Issue, ListedIssueResponseModel>()
                .ForMember(la => la.ImageUrl, opts => opts.MapFrom(a => a.FrontImageData != null ? a.FrontImageData.Url : null));
        }
    }
}