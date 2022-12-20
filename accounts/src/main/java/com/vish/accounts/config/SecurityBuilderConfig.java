package com.vish.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityBuilderConfig {

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.inMemoryAuthentication()
        .withUser("user")
        .password(passwordEncoder().encode("user")) //does no op encoding for password as simple mechanism
        .roles("USER")
        .and()
        .withUser("admin")
         .password(passwordEncoder().encode("admin"))  //does no op encoding for password as simple mechanism
        .roles("ADMIN");
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adminLevel").hasRole("ADMIN")
                .antMatchers("/userLevel").hasAnyRole("USER","ADMIN")
                .antMatchers("/**").authenticated()
                .and().formLogin();
        return http.build();
    }
}
