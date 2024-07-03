package com.poscodx.jblog.config.app;

import com.poscodx.jblog.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
                        .ignoring()
                        .requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                        .requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutUrl("/user/logout")
                .and()

                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/auth")
                .usernameParameter("id")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                // .failureUrl("/user/login?result=fail")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        request.setAttribute("id", request.getParameter("id"));
                        request
                                .getRequestDispatcher("/user/login")
                                .forward(request, response);
                    }
                })
                .and()

                .csrf()
                .disable()

                .authorizeHttpRequests(registry -> {
                    registry
                            /* ACL */
                            // 모든 사용자가 접근 가능
//                            .requestMatchers(new RegexRequestMatcher("^/[^/]+$", null)).permitAll()
//                            .requestMatchers(new RegexRequestMatcher("^/[^/]+/[^/]+$", null)).permitAll()
//                            .requestMatchers(new RegexRequestMatcher("^/[^/]+/[^/]+/[^/]+$", null)).permitAll()

                            // 로그인한 사용자만 접근 가능
                            .requestMatchers(new RegexRequestMatcher("^/[^/]+/admin/basic$", null)).authenticated()
                            .requestMatchers(new RegexRequestMatcher("^/[^/]+/admin/category$", null)).authenticated()
                            .requestMatchers(new RegexRequestMatcher("^/[^/]+/admin/write$", null)).authenticated()

                            .anyRequest()
                            .permitAll();
                });

        return http.build();
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
}
