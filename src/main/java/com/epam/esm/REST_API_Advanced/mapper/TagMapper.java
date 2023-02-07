package com.epam.esm.REST_API_Advanced.mapper;

import com.epam.esm.REST_API_Advanced.dto.TagDto;
import com.epam.esm.REST_API_Advanced.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toDto(Tag tag);
    Tag toEntity(TagDto tagDto);

    Set<Tag> mapToEntity(Set<TagDto> list);
}
