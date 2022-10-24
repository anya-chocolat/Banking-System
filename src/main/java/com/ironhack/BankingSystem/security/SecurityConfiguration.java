package com.ironhack.BankingSystem.security;

import com.ironhack.BankingSystem.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/users/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/accounts/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/accounts/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/accounts/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/transfer").hasAnyRole("CLIENT", "ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/third-party/**").hasAnyRole("THIRD_PARTY", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/check-balance").hasAnyRole("CLIENT", "ADMIN")
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

}
