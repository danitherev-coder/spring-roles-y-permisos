package com.danitherev.jjwt.model.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.danitherev.jjwt.model.dto.auth.request.RegisterDto;
import com.danitherev.jjwt.model.dto.auth.response.RegisterResponse;
import com.danitherev.jjwt.model.entity.User;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "confirmEmail", ignore = true)
    User registerDtoToUser(RegisterDto registerRequest);
    RegisterDto userToRegisterDto(User user);
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "message", ignore = true)
    RegisterResponse registerDtoToRegisterResponse(RegisterDto registerRequest);
}
