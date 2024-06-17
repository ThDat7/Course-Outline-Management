package com.dat.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.dat.controllers",
        "com.dat.repository",
        "com.dat.service",
        "com.dat"
})
@PropertySource("classpath:configs.properties")
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password");

        http.formLogin().defaultSuccessUrl("/")
                .failureUrl("/login?error");

        http.logout().logoutSuccessUrl("/login");
//        http.exceptionHandling()
//                .accessDeniedPage("/login?accessDenied");

        http.authorizeRequests()
                // Admin endpoints
                .antMatchers("/").hasAuthority("ADMIN")
                .antMatchers("/users/**").hasAuthority("ADMIN")
                .antMatchers("/majors/**").hasAuthority("ADMIN")
                .antMatchers("/faculties/**").hasAuthority("ADMIN")
                .antMatchers("/education-programs/**").hasAuthority("ADMIN")
                .antMatchers("/course-outlines/**").hasAuthority("ADMIN")
                .antMatchers("/courses/**").hasAuthority("ADMIN")
                .antMatchers("/assign-outlines/**").hasAuthority("ADMIN")

                .antMatchers("/api/education-programs/remove-outline/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/course-outlines/").hasAuthority("ADMIN")
                .antMatchers("/api/assign-outlines/create/**").hasAuthority("ADMIN")

                // Common endpoints
                .antMatchers("/login", "/logout").permitAll()

                // Generate data and dev test endpoint
                .antMatchers("/data").permitAll()
                .antMatchers("/test").permitAll()

                // Any other requests need authentication
                .anyRequest().authenticated();
//                .anyRequest().permitAll();

        http.csrf().disable();
    }
}
