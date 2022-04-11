package com.example.tangntalk.config;

import com.example.tangntalk.exception.JwtAccessDeniedHandler;
import com.example.tangntalk.exception.JwtAuthenticationEntryPoint;
import com.example.tangntalk.security.jwt.JwtAuthenticationFilter;
import com.example.tangntalk.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] WHITELIST_PATTERNS = {
            "/",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui/swagger-ui.html",
            "/login",
            "/register"
    };

    @Lazy
    private final JwtUtil jwtUtil;

    @Value("${security.enabled}")
    private boolean enableSecurity;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .cors()

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(WHITELIST_PATTERNS).permitAll()
//                .antMatchers("/**").hasAnyAuthority("NOAUTHORITY") // 이 친구로 대체하면 모든 유저가 인가되지 않음
                .antMatchers("/**").hasAuthority("NORMAL")
                .anyRequest().authenticated()
                .and()
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessHandler((request, response, authentication) -> {
                                    Cookie cookie = new Cookie(JwtUtil.JWT_COOKIE_KEY, null);
                                    cookie.setHttpOnly(true);
                                    cookie.setMaxAge(0);
                                    response.addCookie(cookie);
                                    response.setStatus(HttpServletResponse.SC_OK);
                                }
                        )
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling();

    }

}
