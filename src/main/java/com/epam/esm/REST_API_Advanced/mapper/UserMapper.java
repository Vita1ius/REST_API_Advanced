package com.epam.esm.REST_API_Advanced.mapper;

import com.epam.esm.REST_API_Advanced.entity.User;
import com.epam.esm.REST_API_Advanced.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto user);

}
