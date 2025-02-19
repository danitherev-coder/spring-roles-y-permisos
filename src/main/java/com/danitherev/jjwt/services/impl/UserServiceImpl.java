package com.danitherev.jjwt.services.impl;

import com.danitherev.jjwt.config.cache.CacheConfig;
import com.danitherev.jjwt.exceptions.ApiErrors;
import com.danitherev.jjwt.model.dto.user.request.UserDto;
import com.danitherev.jjwt.model.dto.user.response.UserResponse;
import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.model.mapper.user.UserMapper;
import com.danitherev.jjwt.repository.UserRepository;
import com.danitherev.jjwt.services.UserService;
import com.danitherev.jjwt.validations.RoleValidation;
import com.danitherev.jjwt.validations.UserValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidation userValidation;
    @Autowired
    private RoleValidation roleValidation;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserDto userDto) {
        userValidation.validateUserDoesNotExists(userDto.getUsername(), userDto.getEmail());
        
        // Obtener el rol completo de la base de datos
        Role role = roleValidation.getExistingRole(userDto.getRole().getName()); // Aquí se obtiene el rol de la base de datos
    
        User user = userMapper.convertUserDtoToUser(userDto);
        user.setRole(role);  // Asignar el rol completo con su ID
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    
        User savedUser = userRepository.save(user);
        return userMapper.convertUserDtoToUserResponse(userMapper.convertUserToUserDto(savedUser));
    }
    

    @Override
    public UserResponse getById(Long id) {
        System.out.println("Buscar por ID desde la DB");
        User user = userRepository.findById(id)
            .orElseThrow(()-> new ApiErrors(HttpStatus.NOT_FOUND, "El user con el id "+id+" no existe"));

        return userMapper.convertUserDtoToUserResponse(userMapper.convertUserToUserDto(user));
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserDto userDto) {
            // Buscar usuario existente
            User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new ApiErrors(HttpStatus.NOT_FOUND, "El usuario con el id " + id + " no existe"));
    
            // Validar username solo si cambió y no es del mismo usuario
            if (!existingUser.getUsername().equals(userDto.getUsername())) {
                User userWithUsername = userRepository.findByUsername(userDto.getUsername()).orElseThrow(()-> new ApiErrors(HttpStatus.BAD_REQUEST, "El nombre de usuario ya está en uso"));
                if (!userWithUsername.getId().equals(id)) {
                    throw new ApiErrors(HttpStatus.BAD_REQUEST, "El nombre de usuario ya está en uso");
                }
            }
    
            // Validar email solo si cambió y no es del mismo usuario
            if (!existingUser.getEmail().equals(userDto.getEmail())) {
                Optional<User> userWithEmail = userRepository.findByEmail(userDto.getEmail());
                if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
                    throw new ApiErrors(HttpStatus.BAD_REQUEST, "El correo electrónico ya está en uso");
                }
            }
    
            // Actualizar información básica
            existingUser.setUsername(userDto.getUsername());
            existingUser.setFirstName(userDto.getFirstName());
            existingUser.setLastName(userDto.getLastName());
            existingUser.setEmail(userDto.getEmail());
            
            // Actualizar contraseña si se proporciona
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode( userDto.getPassword()));
            }
    
            // Actualizar rol si se proporciona
            if (userDto.getRole() != null && userDto.getRole().getName() != null) {
                Role newRole = roleValidation.getExistingRole(userDto.getRole().getName());
                existingUser.setRole(newRole);
            }
    
            // Guardar y retornar usuario actualizado
            User updatedUser = userRepository.save(existingUser);
            return userMapper.convertUserDtoToUserResponse(userMapper.convertUserToUserDto(updatedUser));
    }

    @Override
    public void delete(Long id) {
        this.getById(id);
        userRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = CacheConfig.USERS_INFO_CACHE, unless = "#result == null")
    public List<UserResponse> getAll() {
        System.out.println("Buscar Users en la DB");

        // Retraso artificial de 2 segundos
        try {
            Thread.sleep(2000); // 2000 ms = 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Error en el retraso artificial: " + e.getMessage());
        }

        List<User> users = userRepository.findAll();

        return userMapper.convertListUserDtoToListUserResponse(userMapper.convertListUserToListUserDto(users));
    }

}
