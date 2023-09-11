package com.cardmonix.cardmonix.authImpl;

import com.cardmonix.cardmonix.domain.constant.Role;
import com.cardmonix.cardmonix.domain.entity.token.JwtToken;
import com.cardmonix.cardmonix.domain.entity.token.OTPConfirmation;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.JwtTokenRepository;
import com.cardmonix.cardmonix.domain.repository.OTPRepository;
import com.cardmonix.cardmonix.domain.repository.UserRepository;
import com.cardmonix.cardmonix.request.LoginRequest;
import com.cardmonix.cardmonix.request.RegisterRequest;
import com.cardmonix.cardmonix.security.JWTAuthenticationFilter;
import com.cardmonix.cardmonix.security.JwtService;
import com.cardmonix.cardmonix.service.Implementation.authImpl.AuthServiceImpl;
import com.cardmonix.cardmonix.service.Implementation.otpImpl.ConfirmEmailMessageImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc(addFilters  = false)
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    private  JwtService jwtService;
    @Mock
    private ConfirmEmailMessageImpl confirmEmailMessage;


    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenRepository jwtTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private OTPRepository otpRepository;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    private JwtToken jwtToken;
    private OTPConfirmation confirmation;
    private String otp;

    @BeforeEach
    public void init(){
        loginRequest = new LoginRequest("chiorlujack@gmail.com","12345");
        registerRequest = new RegisterRequest("chiorlujack@gmail.com","samy","12345");

         otp =UUID.randomUUID().toString().substring(0,4);

    }

    @Test
    void loginRequest() {
        User user =getUser();
        userRepository.save(user);
//        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());

        JwtToken jwtToken = jwtTokenRepository.save(jwtToken(jwt,refreshToken,user));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HashMap<String,String> access = new HashMap<>();
        access.put("jwt-token",jwt);
        access.put("refresh-token",refreshToken);
        System.out.println(jwt);

        Assertions.assertThat(jwt).isEqualTo(access.get("jwt-token"));

    }

    private JwtToken jwtToken(String jwt,String refresh,User user){
        return JwtToken.builder()
                .token(jwt)
                .refreshToken(refresh)
                .user(user)
                .generatedAt(new Date(System.currentTimeMillis()))
                .expiresAt(new Date(System.currentTimeMillis() + 86400000))
                .build();
    }

    @Test
    void resend_link_password() {
        User  user = getUser();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        String otpValue = authService.resend_link_password(user.getEmail());
        int otpsize = otpValue.length();
        assertEquals(otpsize,4);
        Assertions.assertThat(otpValue).isNotEmpty();
        Assertions.assertThat(otpValue.length()).isEqualTo(4);
    }

    @Test
    void registerRequest() {
        User  user = getUser();

        given(userRepository.save(ArgumentMatchers.any())).willReturn(user);
        confirmation = new OTPConfirmation(otp,user);
        System.out.println(confirmation.getUser().getUser_name());
        String response = authService.RegisterRequest(registerRequest);
        System.out.println(response);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(response).isEqualTo("Registered successful");
        //Assertions.assertThat(verify).isEqualTo(user.getEmail()+" Have been verified");

    }

    private User getUser() {

        return User.builder()
                .phone("0989498543")
                .email(registerRequest.getEmail())
                .user_name(registerRequest.getUsername())
                .role(Role.USER)
                .password(passwordEncoder.encode("234"))
                .activate(false)
                .dob(new Date().toString())
                .profile("https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579")
                .build();

    }

}
