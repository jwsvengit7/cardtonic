package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.request.LoginRequest;
import com.cardmonix.cardmonix.request.OtpValidateRequest;
import com.cardmonix.cardmonix.request.RegisterRequest;
import com.cardmonix.cardmonix.response.ResponseFromUser;
import com.cardmonix.cardmonix.security.JWTAuthenticationFilter;
import com.cardmonix.cardmonix.service.AuthService;
import com.cardmonix.cardmonix.service.OtpService;
import com.cardmonix.cardmonix.utils.RandomValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class UserAuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private AuthService authService;

    @MockBean
    private OtpService otpService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;
    private RegisterRequest request;
    private LoginRequest loginRequest;
    private ResponseFromUser responseFromUser;
    private User user;
    private OtpValidateRequest otpValidateRequest;

    private final String EMAIL ="chiorlujack@gmail.com";

    @BeforeEach
    public void init(){
        user  = User.builder().user_name("jwsven")
                .role(Role.USER)
                .password(passwordEncoder.encode("1234"))
                .email(EMAIL)
                .phone("0898490494")
                .build();
        request =  new RegisterRequest(EMAIL,"jwsven","1234");
        loginRequest =  new LoginRequest(EMAIL,"1234");
        responseFromUser = new ResponseFromUser(loginRequest.getEmail(),"jwsven",true,new Date().toString(),"");
        otpValidateRequest =new OtpValidateRequest(RandomValues.generateRandom().substring(0,4),loginRequest.getEmail());

    }

    @Test
    public void createUser() throws Exception {
        given(authService.RegisterRequest(request)).willAnswer((invocationOnMock -> {
            RegisterRequest registerRequest =invocationOnMock.getArgument(0);
            return "Registered successfull";
        }));
        ResultActions response = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",CoreMatchers.is(authService.RegisterRequest(request))))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void loginUser() throws Exception {
        given(authService.LoginRequest(loginRequest)).willAnswer((invocation) -> {
            LoginRequest request = invocation.getArgument(0);
            return responseFromUser;
        });
        ResultActions response = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email",CoreMatchers.is(loginRequest.getEmail())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void generateOtp() throws Exception {
        String otp = UUID.randomUUID().toString().substring(0,4);
        when(authService.resend_link_password(EMAIL)).thenReturn(otp);
        mockMvc.perform(get("/api/v1/auth/regenerate-otp")
                .contentType(MediaType.APPLICATION_JSON)
                .param("email",EMAIL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",CoreMatchers.is(otp)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void verifyUser() throws Exception {

        when(otpService.verifyUserOtp(otpValidateRequest)).thenReturn("chiorlujack@gmail.com");
        mockMvc.perform(post("/api/v1/auth/otp_verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(otpValidateRequest)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());

    }


}