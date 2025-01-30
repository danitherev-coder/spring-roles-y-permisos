package com.danitherev.jjwt.services;

import com.danitherev.jjwt.model.dto.user.request.UserDto;
import com.danitherev.jjwt.model.dto.user.response.UserResponse;

import java.util.List;

public interface UserService{
    UserResponse create(UserDto userDto);
    UserResponse getById(Long id);
    UserResponse update(Long id, UserDto userDto);
    void delete(Long id);
    List<UserResponse> getAll();
}
