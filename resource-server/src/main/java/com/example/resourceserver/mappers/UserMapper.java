package com.example.resourceserver.mappers;


import com.example.resourceserver.dto.UserDto;
import com.example.resourceserver.models.User;
import com.example.resourceserver.models.UserProvider;
import com.example.resourceserver.models.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;

@Mapper(componentModel = "spring", uses = {PasswordEncoderMapperUtil.class},
        imports = {UserRole.class, UserProvider.class, Collections.class})
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", expression = "java(Collections.singletonList(UserRole.USER))")
    @Mapping(target = "provider", expression = "java(UserProvider.LOCAL)")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    User toUser(UserDto userDto);

}
