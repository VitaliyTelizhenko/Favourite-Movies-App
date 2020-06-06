package com.favouritefilms.services;

import com.favouritefilms.entities.User;
import com.favouritefilms.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveNewUserTest() {
        User user = new User();

        boolean isCreated = userService.save(user);

        assertTrue(isCreated);

        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepo, times(1)).save(user);

    }

    @Test
    public void failToSaveExistingUserTest(){
        User user = new User();
        user.setUsername("James");

        doReturn(new User()).when(userRepo).findByUsername("James");

        boolean isCreated = userService.save(user);

        assertFalse(isCreated);
        verify(userRepo, times(0)).save(user);
    }
}