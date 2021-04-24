package ru.chernov.diplom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.chernov.diplom.service.UserService;

/**
 * @author Pavel Chernov
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/profile").authenticated()
                    .anyRequest().permitAll()
//                    .antMatchers("/", "/login", "logout",
//                            "/registration", "/error**", "/css/**", "/js/**", "/img/**", "/templates/**")
//                    .permitAll()
//                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/main", false)
                    .permitAll()
                .and()
                    .rememberMe()
                .and()
                    .logout()
                    .logoutSuccessUrl("/main")
                    .permitAll()
                .and()
                    .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // todo: Add password encoder
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
