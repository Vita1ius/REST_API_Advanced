package com.epam.esm.REST_API_Advanced.security.config;

import com.epam.esm.REST_API_Advanced.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
//                .anyRequest()
//                .authenticated()
                //.anyRequest().permitAll()
                .requestMatchers(HttpMethod.GET,"/tag/**","/gift_certificate/**").permitAll()
                .requestMatchers(HttpMethod.GET).hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST,"/user/order").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.POST).hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE).hasAnyAuthority("ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
