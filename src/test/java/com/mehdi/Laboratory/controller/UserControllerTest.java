package com.mehdi.Laboratory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.dto.register.AddUserResDTO;
import com.mehdi.Laboratory.service.UserServiceImpl;
import com.mehdi.Laboratory.shared.constants.ResponseCodePool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // loads only the web layer, keeping tests lightweight.
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    // Controllers depends on whole Spring web context, thus you can not use @Mock because
    // Spring would need a real UserServiceImpl bean or a MockitoBean in it's context
    // but the service layer does not need entire spring context so it can be figured out only
    // with mockito @Mock annotation and ExtendWith(Mockito.class)
    @MockitoBean
    private UserServiceImpl userService;


    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        AddUserReqDTO user = new AddUserReqDTO("username", "firstname", "lastname", "!$@radAD24a", 17231241241L);

        Mockito.when(userService.save(Mockito.any())).thenReturn(new AddUserResDTO(user.getUsername(), ResponseCodePool.SUCCESS));

//        mockMvc.perform(
//                        post("http://localhost:8080/api/v1/users/register")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(user))
//                )
//                .andExpect(content().string(org.hamcrest.Matchers.containsString(user.getUsername())));

        // Using AssertJ with MockMVc
        String response = mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).contains(user.getUsername());



    }



    @Test
    void shouldReturnBadRequestForUserWithoutUsername() throws Exception {
        AddUserReqDTO user = new AddUserReqDTO("username", "firstname", "lastname", "password", 17231241241L);
        user.setUsername(null);

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForUserWithoutFirstname() throws Exception {
        AddUserReqDTO user = new AddUserReqDTO("username", "firstname", "lastname", "password", 17231241241L);
        user.setFirstName(null);

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("REQUIRED_FIRSTNAME")));
    }

    @Test
    void shouldReturnBadRequestForUserWithoutLastname() throws Exception {
        AddUserReqDTO user = new AddUserReqDTO("username", "firstname", "lastname", "password", 17231241241L);
        user.setLastName(null);

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("REQUIRED_LASTNAME")));
    }

    @Test
    void shouldReturnBadRequestForUserWithoutPassword() throws Exception {
        AddUserReqDTO user = new AddUserReqDTO("username", "firstname", "lastname", "password", 17231241241L);
        user.setPassword(null);

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("REQUIRED_PASSWORD")));
    }

    @Test
    void shouldReturnBadRequestForUserWithoutBirthdate() throws Exception {
        AddUserReqDTO user = new AddUserReqDTO("username", "firstname", "lastname", "password", 0L);
        user.setBirthDate(null);

        // todo mehdi -> check this with mana - is this valid ? or even correct ?
        Mockito.when(userService.save(Mockito.any())).thenThrow(new RuntimeException("REQUIRED_BIRTHDATE"));

        mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("REQUIRED_BIRTHDATE")));
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForPassword")
    void testPasswordComplete(String password, String exceptionMessage) throws Exception {
        // Arrangement
        AddUserReqDTO userDTO = new AddUserReqDTO("testusername", "testfname", "testlname", password, 172351254314L);

        // todo mehdi -> check
        Mockito.when(userService.save(Mockito.any())).thenThrow(new RuntimeException(exceptionMessage));

        // Action / Assertions
        mockMvc.perform(
                        post("http://localhost:8080/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(exceptionMessage)));
    }

    private static Stream<Arguments> provideTestCasesForPassword() {
        return Stream.of(
                Arguments.of("testpassword", "Password can not contain the word password"),
                Arguments.of("testfname", "Password can not contain the First name"),
                Arguments.of("testlname", "Password can not contain the Last name"),
                Arguments.of("testusername", "Password can not contain username")
        );
    }

}