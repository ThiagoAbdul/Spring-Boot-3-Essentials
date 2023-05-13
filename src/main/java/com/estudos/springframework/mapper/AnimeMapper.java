package com.estudos.springframework.mapper;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimePutRequestBody;
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
