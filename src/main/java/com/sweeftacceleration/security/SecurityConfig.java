package com.sweeftacceleration.security;

import com.sweeftacceleration.service.SysUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SysUserServiceImpl sysUserService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(sysUserService);

        return provider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/app/v1/system/user/save", "/app/v1/system/user/active/*")
                .permitAll()

                .antMatchers("/app/v1/system/user", "/app/v1/system/user/remove",
                        "/app/v1/server/servers")
                .hasAnyAuthority("ADMIN", "SUPER_ADMIN", "USER")

                .antMatchers("/app/v1/system/role/save", "/app/v1/system/delete/*",
                        "/app/v1/system/role/**",
                        "/app/v1/system/deactivation/*", "/app/v1/system/activation/*",
                        "/app/v1/system/server/**")
                .hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .anyRequest().authenticated().and().formLogin()
                .defaultSuccessUrl("http://localhost:8080/swagger-ui.html", true);

    }

}