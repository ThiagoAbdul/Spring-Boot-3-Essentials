package com.estudos.springinitializr.mapper;

import com.estudos.springinitializr.domain.Anime;
import com.estudos.springinitializr.request.AnimePostRequestBody;
import com.estudos.springinitializr.request.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimeMapper {

    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    Anime toAnime(AnimePostRequestBody anime);
    Anime toAnime(AnimePutRequestBody anime);

}
