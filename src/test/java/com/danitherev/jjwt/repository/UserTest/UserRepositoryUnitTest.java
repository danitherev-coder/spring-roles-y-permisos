package com.danitherev.jjwt.repository.UserTest;

import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;

import com.danitherev.jjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryUnitTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this); // Inicializar mocks
    }

    @Test
    void shouldFindByUsername(){
        // Arranque: Crear primero un usuario simulado
        Role role = new Role();
        role.setName("USER");

        User mockUser = new User();
        mockUser.setUsername("danitherev");
        mockUser.setFirstName("Daniel");
        mockUser.setLastName("Saavedra");
        mockUser.setEmail("danitherev@example.com");
        mockUser.setPassword("password123");
        mockUser.setRole(role);

        // Simular el comportamiento del metodo findByUsername con Mockito
        when(userRepository.findByUsername("danitherev")).thenReturn(Optional.of(mockUser));

        // Actuar: Llamar al metodo del repositorio
        Optional<User> foundUser = userRepository.findByUsername("danitherev");

        // Assert: Verificar que se devuelve el usuario esperado
        assertTrue(foundUser.isPresent());
        assertEquals("danitherev", foundUser.get().getUsername());
        assertEquals("danitherev@example.com", foundUser.get().getEmail());
        assertEquals("Daniel", foundUser.get().getFirstName());

        // Verificar que el repositorio fue llamado exactamente una vez
        verify(userRepository, times(1)).findByUsername("danitherev");

    }

    @Test
    void shouldNotFoundByUsername(){
        // Simular el comportamiento si no existe el username
        when(userRepository.findByUsername("noexistuser")).thenReturn(Optional.empty());
        // Actuar: Llamar al metodo
        Optional<User> notFoundUser = userRepository.findByUsername("noexistuser");
        // Assert: Verificar que no exista el user
        assertFalse(notFoundUser.isPresent());
        // Verificar que el repositorio fue llamado exactamente una vez
        verify(userRepository, times(1)).findByUsername("noexistuser");
    }

    @Test
    void shouldFindByEmail(){
        // Arrange: Crear un usuario simulado
        Role role = new Role();
        role.setName("ADMIN");

        User mockUser = new User();
        mockUser.setUsername("danitherev");
        mockUser.setFirstName("Daniel");
        mockUser.setLastName("Saavedra");
        mockUser.setEmail("danitherev2@example.com");
        mockUser.setPassword("password123");
        mockUser.setRole(role);

        // Simular el comportamiento
        when(userRepository.findByEmail("danitherev2@example.com")).thenReturn(Optional.of(mockUser));

        // Act: Llamar al repositorio
        Optional<User> foundUser = userRepository.findByEmail("danitherev2@example.com");

        // Assert
        assertEquals("danitherev2@example.com", foundUser.get().getEmail());

        // Verificar que se llame una sola vez al metodo
        verify(userRepository, times(1)).findByEmail("danitherev2@example.com");
    }

    @Test
    void shouldNotFindByEmail(){
        when(userRepository.findByEmail("noexistemail@mail.com")).thenReturn(Optional.empty());

        Optional<User> notFound = userRepository.findByEmail("noexistemail@mail.com");

        assertFalse(notFound.isPresent());

    }

    @Test
    void existEmail(){
        when(userRepository.existsByEmail("danitherev2@example.com")).thenReturn(true);

        // Act
        boolean existUser = userRepository.existsByEmail("danitherev2@example.com");
        // Assert
        assertTrue(existUser);
        // Verificar
        verify(userRepository, times(1)).existsByEmail("danitherev2@example.com");
    }
}
