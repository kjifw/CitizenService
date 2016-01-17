namespace CitizenService.Web.Api.Infrastructure.Mappings
{
    using System.Collections.Generic;
    using System.Linq;

    using AutoMapper;
    using CitizenService.Models;

    public class CustomUrlResolver : ValueResolver<ICollection<ImageData>, string>
    {
        protected override string ResolveCore(ICollection<ImageData> source)
        {
            return source.FirstOrDefault().Url;
        }
    }
}