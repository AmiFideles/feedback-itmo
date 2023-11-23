package ru.niu.itmo.feedback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.niu.itmo.feedback.dto.UserDto;
import ru.niu.itmo.feedback.entity.security.User;
import ru.niu.itmo.feedback.security.details.CustomUserDetails;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);
    UserDto toUser(CustomUserDetails customUserDetails);
    UserDto toDto(User user);
}
