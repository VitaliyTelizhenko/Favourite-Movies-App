package com.favouritefilms.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RegistrationControllerTest {

    @Autowired
    private RegistrationController homeController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void redirectWithoutLoginTest() throws Exception {

        this.mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void correctLoginTest() throws Exception {

        this.mockMvc.perform(formLogin().user("t").password("t"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void wrongLoginOrPasswordTest() throws Exception {

        this.mockMvc.perform(formLogin().user("t").password("y"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-error"));

        this.mockMvc.perform(formLogin().user("i").password("t"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-error"));
    }

    @Test
    public void registrationTest() throws Exception{

        MockHttpServletRequestBuilder post = post("/registration")
                .param("username", "k")
                .param("password", "k")
                .param("firstName", "k")
                .param("lastName", "k")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void registrationWithExistingUsernameTest() throws Exception{

        MockHttpServletRequestBuilder post = post("/registration")
                .param("username", "t")
                .param("password", "t")
                .param("firstName", "t")
                .param("lastName", "t")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(content().string(containsString("User with this username already exists")));
    }

    @Test
    public void invalidInputsOnRegistrationTest() throws Exception{

        MockHttpServletRequestBuilder post = post("/registration")
                .param("username", "")
                .param("password", "")
                .param("firstName", "")
                .param("lastName", "")
                .with(csrf());

        this.mockMvc.perform(post)
                .andDo(print())
                .andExpect(content().string(containsString("Username is required")))
                .andExpect(content().string(containsString("Password is required")))
                .andExpect(content().string(containsString("Firstname is required")))
                .andExpect(content().string(containsString("Lastname is required")));
    }


}