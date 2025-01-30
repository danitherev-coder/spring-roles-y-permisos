package com.danitherev.jjwt.services.impl;

import com.danitherev.jjwt.model.dto.user.request.UserDto;
import com.danitherev.jjwt.model.dto.user.response.UserResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.model.mapper.user.UserMapper;
import com.danitherev.jjwt.repository.UserRepository;
import com.danitherev.jjwt.services.UserService;
import com.danitherev.jjwt.validations.RoleValidation;
import com.danitherev.jjwt.validations.UserValidation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final RoleValidation roleValidation;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserDto userDto) {
        userValidation.validateUserDoesNotExists(userDto.getUsername(), userDto.getEmail());
        
        // Obtener el rol completo de la base de datos
        Role role = roleValidation.getExistingRole(userDto.getRole().getName()); // Aquí se obtiene el rol de la base de datos
    
        User user = userMapper.convertUserDtoToUser(userDto);
        user.setRole(role);  // Asignar el rol completo con su ID
    
        User savedUser = userRepository.save(user);
        return userMapper.convertUserDtoToUserResponse(userMapper.convertUserToUserDto(savedUser));
    }
    

    @Override
    public UserResponse getById(Long id) {
        return null;
    }

    @Override
    public UserResponse update(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO document why this method is empty
    }

    @Override
    public List<UserResponse> getAll() {
        return List.of();
    }
}
