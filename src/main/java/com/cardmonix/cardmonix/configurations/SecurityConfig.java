package com.cardmonix.cardmonix.configurations;

import com.cardmonix.cardmonix.security.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthEntryPoint authEntryPoint;
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity){
        httpSecurity.cors(cor -> cor.configure(httpSecurity));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorize ->
                authorize.requestMatchers(
                                "/api/v1/auth/**", "/api/v1/password/**", "/api/v1/giftcards/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        )
                        .permitAll()
                        .requestMatchers("/api/v1/user/veiw-coin", "/api/v1/user/search-coin")
                        .permitAll()
                        .requestMatchers("/api/v1/user/user-details", "/upload-picture", "/update-profile")
                        .hasAnyAuthority("USER", "ADMIN")

                        .requestMatchers("/api/v1/admin/**")

                        .hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                        .and()
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class));
        return httpSecurity.build();
    }
}
