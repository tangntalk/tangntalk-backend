package com.example.tangntalk.security.config;

import com.example.tangntalk.security.jwt.JwtAuthenticationFilter;
import com.example.tangntalk.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String[] WHITELIST_PATTERNS = {
            "/",
            "/error",
            "/favicon.ico",
            "/static/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui/swagger-ui.html",
            "/login",
            "/register"
    };

    private final JwtUtil jwtUtil;
    private final boolean enableSecurity;

    public SecurityConfiguration(@Lazy JwtUtil jwtUtil, @Value("${security.enabled}") boolean enableSecurity) {
        this.jwtUtil = jwtUtil;
        this.enableSecurity = enableSecurity;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if (!enableSecurity) {
            web.ignoring().antMatchers("/**");
        }
        super.configure(web);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(WHITELIST_PATTERNS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling();

    }

}
