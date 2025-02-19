package com.danitherev.jjwt.repository.UserTest;

import com.danitherev.jjwt.model.entity.Role;
import com.danitherev.jjwt.model.entity.User;
import com.danitherev.jjwt.repository.RoleRepository;
import com.danitherev.jjwt.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void saveUserTest(){
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("Daniel");
        user.setLastName("Chu");
        user.setUsername("danitherev");
        user.setEmail("danitherev98@gmail.com");
        user.setPassword("1234567");
        user.setRole(role);

        userRepository.save(user);

        // Act: buscar usuario por username si se guardo
        Optional<User> foundUser = userRepository.findByUsername("danitherev");
        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("danitherev", foundUser.get().getUsername());
    }

}
