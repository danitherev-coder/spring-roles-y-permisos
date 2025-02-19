package com.danitherev.jjwt.model.mapper.user;

import com.danitherev.jjwt.model.dto.user.request.UserDto;
import com.danitherev.jjwt.model.dto.user.response.UserResponse;
import com.danitherev.jjwt.model.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Métodos de conversión para un único objeto
    @Mapping(source = "role", target = "role")
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "confirmEmail", ignore = true)
    User convertUserDtoToUser(UserDto userDto);
    
    UserDto convertUserToUserDto(User user);

    @Mapping(source = "role", target = "role")
    UserResponse convertUserDtoToUserResponse(UserDto userDto);

    @Mapping(source = "role", target = "role")
    UserResponse convertUserToUserResponse(User user);

    // Métodos de conversión para lista
    List<User> listUserDtoToListUser(List<UserDto> listUserDto);
    List<UserDto> convertListUserToListUserDto(List<User> listUser);
    List<UserResponse> convertListUserDtoToListUserResponse(List<UserDto> listUserDto);
    List<UserResponse> convertListUserToListUserResponse(List<User> listUser);
}




