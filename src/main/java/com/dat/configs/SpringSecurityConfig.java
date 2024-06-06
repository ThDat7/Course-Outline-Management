package com.dat.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
//                .antMatchers("/").hasAuthority("ADMIN")
//                .antMatchers("/users/**").hasAuthority("ADMIN")
//                .antMatchers("/majors/**").hasAuthority("ADMIN")
//                .antMatchers("/faculties/**").hasAuthority("ADMIN")
//                .antMatchers("/education-programs/**").hasAuthority("ADMIN")
//                .antMatchers("/course-outlines/**").hasAuthority("ADMIN")
//                .antMatchers("/courses/**").hasAuthority("ADMIN")
//                .antMatchers("/assign-outlines/**").hasAuthority("ADMIN")
//                .antMatchers("/api/education-programs/remove-outline/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/course-outlines/").hasAuthority("ADMIN")
//                .antMatchers("/api/assign-outlines/create/**").hasAuthority("ADMIN")
//
//                // Teacher endpoints
//                .antMatchers("/api/profile/teacher").hasAuthority("TEACHER")
//                .antMatchers("/api/profile/additional-info/teacher").hasAuthority("TEACHER")
//                .antMatchers(HttpMethod.GET, "/api/course-outlines/").hasAuthority("TEACHER")
//                .antMatchers("/api/course-outlines/*").hasAuthority("TEACHER")
//
//                // Student endpoints
//                .antMatchers("/api/profile/student").hasAuthority("STUDENT")
//                .antMatchers("/api/profile/additional-info/student").hasAuthority("STUDENT")
//
//                // authenticated endpoints
//                .antMatchers("/api/search/**").authenticated()
//                .antMatchers("/api/comments/**").authenticated()
//                .antMatchers("/api/course-outlines/view/**").authenticated()
//                .antMatchers("/api/education-programs/view/**").authenticated()
//
//                // Common endpoints
//                .antMatchers("/login", "/logout").permitAll()
//                .antMatchers("/api/login", "/api/registry").permitAll()
//
//                // Generate data endpoint
//                .antMatchers("/data").permitAll()
//
//                // Any other requests need authentication
//                .anyRequest().authenticated();
                .anyRequest().permitAll();

        http.csrf().disable();
    }
}
